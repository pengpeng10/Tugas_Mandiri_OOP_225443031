import java.time.LocalDate

// Entitas transaksi peminjaman menghubungkan Item, Member, dan TransactionStatus
class Transaction(
    val id: String,
    val item: Item,
    val member: Member,
    val borrowDate: String = LocalDate.now().toString(),
    var status: TransactionStatus = TransactionStatus.Borrowed
) {

    // Proses pengembalian item dan update status transaksi
    fun returnItem(daysLate: Int = 0): Double {
        if (status.isFinal()) {
            println("[ERROR] Transaksi $id sudah berstatus final (${status.display()}) dan tidak dapat dikembalikan lagi.")
            return 0.0
        }

        val fine = item.returnItem(daysLate)
        status = if (daysLate > 0) {
            TransactionStatus.Overdue(daysLate)
        } else {
            TransactionStatus.Returned
        }
        return fine
    }

    // Membatalkan transaksi yang belum final
    fun cancel() {
        if (status.isFinal()) {
            println("[ERROR] Transaksi $id sudah berstatus final (${status.display()}) dan tidak dapat dibatalkan.")
            return
        }

        status = TransactionStatus.Cancelled
        item.returnItem(0)
        println("[INFO] Transaksi $id berhasil dibatalkan.")
    }

    // Menampilkan detail transaksi ke konsol
    fun displayTransaction() {
        println("----------------------------------------")
        println("ID Transaksi     : $id")
        println("Tanggal Pinjam   : $borrowDate")
        println("Peminjam         : ${member.name} (ID: ${member.id})")
        println("Item             : ${item.title} (ID: ${item.id}, Jenis: ${item.getItemType()})")
        println("Status           : ${status.display()}")

        val currentStatus = status
        if (currentStatus is TransactionStatus.Overdue) {
            val fine = currentStatus.daysLate * item.calculateFinePerDay()
            println("Keterlambatan    : ${currentStatus.daysLate} hari")
            println("Total Denda      : Rp %,.0f".format(fine))
        }
        println("----------------------------------------")
    }
}
