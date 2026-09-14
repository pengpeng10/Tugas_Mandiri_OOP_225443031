// Subclass Item untuk koleksi DVD multimedia (Inheritance & Polymorphism)
class DVD(
    id: String,
    title: String,
    year: Int,
    val director: String,
    val duration: Int,
    val genre: String
) : Item(id, title, year) {

    // Denda keterlambatan DVD: Rp 5.000/hari
    override fun calculateFinePerDay(): Double = 5000.0

    override fun getItemType(): String = "DVD"

    // Batas pinjam DVD: 3 hari
    override fun getMaxBorrowDays(): Int = 3

    // Menampilkan atribut spesifik DVD
    override fun displayInfo() {
        super.displayInfo()
        println("Sutradara        : $director")
        println("Durasi           : $duration menit")
        println("Genre            : $genre")
        println("----------------------------------------")
    }
}
