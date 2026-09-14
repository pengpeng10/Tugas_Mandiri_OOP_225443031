// Subclass Item untuk koleksi Jurnal Ilmiah (Inheritance & Polymorphism)
class Journal(
    id: String,
    title: String,
    year: Int,
    val publisher: String,
    val volume: Int,
    val issueNumber: Int
) : Item(id, title, year) {

    // Denda keterlambatan jurnal: Rp 3.000/hari
    override fun calculateFinePerDay(): Double = 3000.0

    override fun getItemType(): String = "Jurnal"

    // Batas pinjam jurnal: 7 hari
    override fun getMaxBorrowDays(): Int = 7

    // Menampilkan atribut spesifik jurnal
    override fun displayInfo() {
        super.displayInfo()
        println("Penerbit         : $publisher")
        println("Volume           : $volume")
        println("Nomor Edisi      : $issueNumber")
        println("----------------------------------------")
    }
}
