// Superclass abstrak untuk koleksi perpustakaan (Abstraksi & Enkapsulasi)
abstract class Item(
    val id: String,
    val title: String,
    val year: Int
) {
    // Status ketersediaan dengan private set (Enkapsulasi)
    var isAvailable: Boolean = true
        private set

    // Perhitungan denda per hari (Polimorfisme)
    abstract fun calculateFinePerDay(): Double

    // Mendapatkan tipe koleksi (Buku, Jurnal, DVD)
    abstract fun getItemType(): String

    // Batas maksimal peminjaman (hari)
    abstract fun getMaxBorrowDays(): Int

    // Meminjam item jika tersedia
    fun borrow(): Boolean {
        return if (isAvailable) {
            isAvailable = false
            println("[SUKSES] Item '$title' (ID: $id) berhasil dipinjam.")
            true
        } else {
            println("[GAGAL] Item '$title' (ID: $id) sedang tidak tersedia untuk dipinjam.")
            false
        }
    }

    // Mengembalikan item dan menghitung denda jika terlambat
    fun returnItem(daysLate: Int = 0): Double {
        return if (!isAvailable) {
            isAvailable = true
            val fine = if (daysLate > 0) daysLate * calculateFinePerDay() else 0.0
            println("[SUKSES] Item '$title' (ID: $id) berhasil dikembalikan.")
            if (fine > 0.0) {
                println("[DENDA] Keterlambatan $daysLate hari dikenakan denda sebesar: Rp %,.0f".format(fine))
            }
            fine
        } else {
            println("[PERINGATAN] Item '$title' (ID: $id) tidak sedang dipinjam.")
            0.0
        }
    }

    // Menampilkan informasi detail item
    open fun displayInfo() {
        val statusStr = if (isAvailable) "Tersedia" else "Sedang Dipinjam"
        println("----------------------------------------")
        println("ID Item          : $id")
        println("Jenis Item       : ${getItemType()}")
        println("Judul            : $title")
        println("Tahun            : $year")
        println("Status           : $statusStr")
        println("Denda / Hari     : Rp %,.0f".format(calculateFinePerDay()))
        println("Maksimal Pinjam  : ${getMaxBorrowDays()} hari")
    }
}
