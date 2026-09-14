// Sealed class untuk status transaksi peminjaman perpustakaan (exhaustive when)
sealed class TransactionStatus {

    // Menampilkan deskripsi status
    abstract fun display(): String

    // Cek apakah transaksi sudah selesai (Returned / Cancelled)
    fun isFinal(): Boolean = this is Returned || this is Cancelled

    // Item sedang aktif dipinjam
    object Borrowed : TransactionStatus() {
        override fun display(): String = "Dipinjam"
    }

    // Item telah dikembalikan
    object Returned : TransactionStatus() {
        override fun display(): String = "Sudah Dikembalikan"
    }

    // Item dikembalikan terlambat
    data class Overdue(val daysLate: Int) : TransactionStatus() {
        override fun display(): String = "Terlambat ($daysLate hari)"
    }

    // Transaksi dibatalkan
    object Cancelled : TransactionStatus() {
        override fun display(): String = "Dibatalkan"
    }
}
