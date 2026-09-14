// Pengelola operasional sistem perpustakaan digital (Item, Anggota, Transaksi)
class Library(
    val name: String
) {
    // Koleksi internal (Enkapsulasi)
    private val items: MutableList<Item> = mutableListOf()
    private val members: MutableList<Member> = mutableListOf()
    private val transactions: MutableList<Transaction> = mutableListOf()

    // Properti terhitung (computed properties)
    val totalItems: Int get() = items.size
    val availableItems: Int get() = items.count { it.isAvailable }
    val totalMembers: Int get() = members.size
    val totalTransactions: Int get() = transactions.size

    // Menambahkan item baru ke perpustakaan
    fun addItem(item: Item) {
        items.add(item)
        println("[INFO] Item '${item.title}' (ID: ${item.id}) berhasil didaftarkan ke perpustakaan.")
    }

    // Menambahkan banyak item sekaligus (vararg)
    fun addItems(vararg newItems: Item) {
        for (item in newItems) {
            addItem(item)
        }
    }

    // Mencari item berdasarkan ID
    fun findItem(id: String): Item? {
        return items.find { it.id.equals(id, ignoreCase = true) }
    }

    // Mencari item berdasarkan judul atau ID
    fun searchItems(keyword: String): List<Item> {
        return items.filter {
            it.title.contains(keyword, ignoreCase = true) || it.id.contains(keyword, ignoreCase = true)
        }
    }

    // Mendaftarkan anggota baru
    fun registerMember(id: String, name: String, email: String, phone: String): Boolean {
        if (members.any { it.id.equals(id, ignoreCase = true) }) {
            println("[ERROR] Registrasi gagal: Anggota dengan ID '$id' sudah terdaftar.")
            return false
        }

        val newMember = Member(id, name, email, phone)
        members.add(newMember)
        println("[SUKSES] Anggota '$name' (ID: $id) berhasil didaftarkan.")
        return true
    }

    // Mencari anggota berdasarkan ID
    fun findMember(id: String): Member? {
        return members.find { it.id.equals(id, ignoreCase = true) }
    }

    // Memproses peminjaman item oleh anggota
    fun borrowItem(memberId: String, itemId: String): Transaction? {
        val member = findMember(memberId)
        val item = findItem(itemId)

        if (member == null) {
            println("[ERROR] Peminjaman gagal: Anggota dengan ID '$memberId' tidak ditemukan.")
            return null
        }
        if (item == null) {
            println("[ERROR] Peminjaman gagal: Item dengan ID '$itemId' tidak ditemukan.")
            return null
        }

        val transaction = member.borrowItem(item)
        if (transaction != null) {
            transactions.add(transaction)
            return transaction
        }
        return null
    }

    // Memproses pengembalian item oleh anggota
    fun returnItem(memberId: String, itemId: String, daysLate: Int = 0): Double {
        val member = findMember(memberId)
        val item = findItem(itemId)

        if (member == null) {
            println("[ERROR] Pengembalian gagal: Anggota dengan ID '$memberId' tidak ditemukan.")
            return 0.0
        }
        if (item == null) {
            println("[ERROR] Pengembalian gagal: Item dengan ID '$itemId' tidak ditemukan.")
            return 0.0
        }

        return member.returnItem(item, daysLate)
    }

    // Menampilkan seluruh koleksi item
    fun displayAllItems() {
        println("DAFTAR SELURUH ITEM PERPUSTAKAAN")
        println("Total Item: $totalItems | Tersedia: $availableItems | Dipinjam: ${totalItems - availableItems}")
        if (items.isEmpty()) {
            println("Belum ada item di perpustakaan.")
        } else {
            for (item in items) {
                item.displayInfo()
            }
        }
    }

    // Menampilkan item yang sedang tersedia
    fun displayAvailableItems() {
        println("==================================================")
        println("DAFTAR ITEM YANG TERSEDIA")
        println("Jumlah Tersedia: $availableItems item")
        println("==================================================")
        val availableList = items.filter { it.isAvailable }
        if (availableList.isEmpty()) {
            println("Tidak ada item yang sedang tersedia.")
        } else {
            for (item in availableList) {
                println("[${item.id}] ${item.title} (${item.getItemType()}, ${item.year}) - Maks: ${item.getMaxBorrowDays()} hari | Denda: Rp %,.0f/hari".format(item.calculateFinePerDay()))
            }
        }
        println("--------------------------------------------------")
    }

    // Menampilkan seluruh anggota terdaftar
    fun displayAllMembers() {
        println("==================================================")
        println("DAFTAR ANGGOTA PERPUSTAKAAN")
        println("Total Anggota Terdaftar: $totalMembers")
        println("==================================================")
        if (members.isEmpty()) {
            println("Belum ada anggota terdaftar.")
        } else {
            for (member in members) {
                member.displayInfo()
            }
        }
    }

    // Menampilkan seluruh transaksi perpustakaan
    fun displayAllTransactions() {
        println("==================================================")
        println("DAFTAR SELURUH TRANSAKSI PERPUSTAKAAN")
        println("Total Transaksi: $totalTransactions")
        println("==================================================")
        if (transactions.isEmpty()) {
            println("Belum ada transaksi tercatat.")
        } else {
            for (tx in transactions) {
                tx.displayTransaction()
            }
        }
    }

    // Menampilkan laporan ringkasan statistik
    fun displayReport() {
        val totalFineCollected = members.sumOf { it.totalFines }
        println(" ===  LAPORAN STATISTIK PERPUSTAKAAN  === ")
        println("Nama Perpustakaan      : $name")
        println("Total Koleksi Item     : $totalItems")
        println("  - Item Tersedia      : $availableItems")
        println("  - Item Dipinjam      : ${totalItems - availableItems}")
        println("Total Anggota Terdaftar: $totalMembers")
        println("Total Transaksi Dibuat : $totalTransactions")
        println("Total Akumulasi Denda  : Rp %,.0f".format(totalFineCollected))
        println("==================================================")
    }
}
