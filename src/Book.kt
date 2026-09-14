// Subclass Item untuk koleksi Buku (Inheritance & Polymorphism)
class Book(
    id: String,
    title: String,
    year: Int,
    val author: String,
    val pages: Int,
    val genre: String
) : Item(id, title, year) {

    // Denda keterlambatan buku: Rp 2.000/hari
    override fun calculateFinePerDay(): Double = 2000.0

    override fun getItemType(): String = "Buku"

    // Batas pinjam buku: 14 hari
    override fun getMaxBorrowDays(): Int = 14

    // Menampilkan atribut spesifik buku
    override fun displayInfo() {
        super.displayInfo()
        println("Penulis          : $author")
        println("Jumlah Halaman   : $pages")
        println("Genre            : $genre")
        println("----------------------------------------")
    }
}
