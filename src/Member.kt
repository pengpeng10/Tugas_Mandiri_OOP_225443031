// Representasi anggota perpustakaan dengan enkapsulasi data pribadi
class Member(
    val id: String,
    val name: String,
    private val email: String,
    private val phone: String
) {
    // Riwayat transaksi disimpan private (Enkapsulasi)
    private val transactions: MutableList<Transaction> = mutableListOf()

    // Jumlah total transaksi
    val transactionCount: Int
        get() = transactions.size

    // Akumulasi total denda yang terlambat
    val totalFines: Double
        get() = transactions.sumOf { tx ->
            val currentStatus = tx.status
            if (currentStatus is TransactionStatus.Overdue) {
                currentStatus.daysLate * tx.item.calculateFinePerDay()
            } else {
                0.0
            }
        }

    // Jumlah item yang sedang aktif dipinjam
    val activeBorrows: Int
        get() = transactions.count { it.status is TransactionStatus.Borrowed }

    // Getter resmi untuk akses data private
    fun getEmail(): String = email
    fun getPhone(): String = phone

    // Meminjam item (maksimal 3 pinjaman aktif)
    fun borrowItem(item: Item): Transaction? {
        if (!item.isAvailable) {
            println("[ERROR] Peminjaman gagal: Item '${item.title}' sedang tidak tersedia.")
            return null
        }

        if (activeBorrows >= 3) {
            println("[ERROR] Peminjaman gagal: Anggota $name telah mencapai batas maksimal 3 peminjaman aktif.")
            return null
        }

        val isBorrowed = item.borrow()
        return if (isBorrowed) {
            val transactionId = "TRX-${System.currentTimeMillis()}"
            val newTransaction = Transaction(
                id = transactionId,
                item = item,
                member = this
            )
            transactions.add(newTransaction)
            println("[SUKSES] Transaksi baru dibuat dengan ID: $transactionId untuk anggota: $name")
            newTransaction
        } else {
            null
        }
    }

    // Mengembalikan item yang dipinjam anggota
    fun returnItem(item: Item, daysLate: Int = 0): Double {
        val activeTx = transactions.find { it.status is TransactionStatus.Borrowed && it.item.id == item.id }
        return if (activeTx != null) {
            activeTx.returnItem(daysLate)
        } else {
            println("[ERROR] Tidak ditemukan transaksi aktif untuk item '${item.title}' pada anggota $name.")
            0.0
        }
    }

    // Mengembalikan daftar transaksi immutable
    fun getTransactions(): List<Transaction> = transactions.toList()

    // Menampilkan ringkasan profil anggota
    fun displayInfo() {
        println("========================================")
        println("PROFIL ANGGOTA")
        println("ID Anggota       : $id")
        println("Nama             : $name")
        println("Email            : ${getEmail()}")
        println("Telepon          : ${getPhone()}")
        println("Total Transaksi  : $transactionCount")
        println("Pinjaman Aktif   : $activeBorrows / 3")
        println("Total Denda      : Rp %,.0f".format(totalFines))
        println("========================================")
    }

    // Menampilkan riwayat transaksi anggota
    fun displayTransactions() {
        println("========================================")
        println("RIWAYAT TRANSAKSI ANGGOTA: $name (ID: $id)")
        println("========================================")
        if (transactions.isEmpty()) {
            println("Belum ada riwayat transaksi.")
        } else {
            for (tx in transactions) {
                tx.displayTransaction()
            }
        }
    }
}
