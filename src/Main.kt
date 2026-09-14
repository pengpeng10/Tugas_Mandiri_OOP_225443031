fun main() {
    val library = Library(name = "Perpustakaan Kampus")

    // Inisialisasi data koleksi perpustakaan
    val book1 = Book(
        id = "B001",
        title = "Pemrograman Kotlin",
        year = 2023,
        author = "Budi Santoso",
        pages = 350,
        genre = "Programming"
    )
    val book2 = Book(
        id = "B002",
        title = "Dasar-Dasar OOP",
        year = 2022,
        author = "Siti Rahayu",
        pages = 280,
        genre = "Education"
    )
    val journal1 = Journal(
        id = "J001",
        title = "Jurnal Teknologi Informasi",
        year = 2023,
        publisher = "ITB",
        volume = 15,
        issueNumber = 2
    )
    val journal2 = Journal(
        id = "J002",
        title = "Jurnal Pendidikan",
        year = 2022,
        publisher = "UGM",
        volume = 10,
        issueNumber = 1
    )
    val dvd1 = DVD(
        id = "D001",
        title = "Inception",
        year = 2010,
        director = "Christopher Nolan",
        duration = 148,
        genre = "Sci-Fi"
    )
    val dvd2 = DVD(
        id = "D002",
        title = "The Matrix",
        year = 1999,
        director = "Wachowski",
        duration = 136,
        genre = "Action"
    )

    library.addItems(book1, book2, journal1, journal2, dvd1, dvd2)

    // Registrasi anggota perpustakaan
    library.registerMember(id = "M001", name = "Ahmad Fauzi", email = "ahmad@email.com", phone = "08123456789")
    library.registerMember(id = "M002", name = "Dewi Lestari", email = "dewi@email.com", phone = "08129876543")
    library.registerMember(id = "M003", name = "Rizky Pratama", email = "rizky@email.com", phone = "08125678901")

    // Tampilkan katalog awal
    println("\n=== KATALOG ITEM PERPUSTAKAAN ===")
    library.displayAllItems()

    // Skenario transaksi peminjaman
    println("\n=== TRANSAKSI PEMINJAMAN ===")
    library.borrowItem(memberId = "M001", itemId = "B001")
    library.borrowItem(memberId = "M001", itemId = "D001")
    library.borrowItem(memberId = "M002", itemId = "J001")
    library.borrowItem(memberId = "M003", itemId = "B002")

    // Ketersediaan koleksi setelah peminjaman
    println("\n=== ITEM TERSEDIA ===")
    library.displayAvailableItems()

    // Riwayat transaksi aktif anggota
    println("\n=== RIWAYAT TRANSAKSI ANGGOTA ===")
    val memberAhmad = library.findMember(id = "M001")
    val memberDewi = library.findMember(id = "M002")

    memberAhmad?.displayTransactions()
    memberDewi?.displayTransactions()

    // Skenario pengembalian (tepat waktu & terlambat)
    println("\n=== TRANSAKSI PENGEMBALIAN ===")
    library.returnItem(memberId = "M001", itemId = "B001", daysLate = 0)
    library.returnItem(memberId = "M002", itemId = "J001", daysLate = 3)

    // Status transaksi setelah pengembalian
    println("\n=== STATUS TRANSAKSI SETELAH PENGEMBALIAN ===")
    memberAhmad?.displayTransactions()
    memberDewi?.displayTransactions()

    // Polimorfisme: evaluasi dinamis method calculateFinePerDay() & getMaxBorrowDays()
    println("\n=== ATURAN & TARIF KOLEKSI (POLIMORFISME) ===")
    val sampleItems: List<Item> = listOfNotNull(
        library.findItem(id = "B001"),
        library.findItem(id = "J001"),
        library.findItem(id = "D001")
    )
    for (item in sampleItems) {
        println("- [${item.id}] ${item.getItemType()} '${item.title}' | Batas: ${item.getMaxBorrowDays()} hari | Denda: Rp %,.0f/hari".format(item.calculateFinePerDay()))
    }

    // Sealed Class: evaluasi status dengan exhaustive when
    println("\n=== SIKLUS STATUS TRANSAKSI (SEALED CLASS) ===")
    val sampleStatuses: List<TransactionStatus> = listOf(
        TransactionStatus.Borrowed,
        TransactionStatus.Returned,
        TransactionStatus.Overdue(daysLate = 5),
        TransactionStatus.Cancelled
    )
    for (status in sampleStatuses) {
        val statusDescription = when (status) {
            is TransactionStatus.Borrowed -> "Sedang aktif dipinjam"
            is TransactionStatus.Returned -> "Telah dikembalikan ke rak"
            is TransactionStatus.Overdue -> "Terlambat (${status.daysLate} hari)"
            is TransactionStatus.Cancelled -> "Transaksi dibatalkan"
        }
        println("- ${status.display()} -> $statusDescription (Final: ${status.isFinal()})")
    }

    // Smart Casting & Safe Cast: mengambil data dari sistem (simulasi query database/koleksi)
    println("\n=== INSPEKSI TIPE KOLEKSI (SMART CASTING) ===")
    val queriedItem: Item? = library.findItem(id = "B001")
    val resolvedItem = queriedItem ?: run {
        println("[ERROR] Item B001 tidak ditemukan dalam sistem.")
        return
    }

    // Smart cast otomatis sesuai subtype Item
    when (resolvedItem) {
        is Book -> println("Buku Terverifikasi: '${resolvedItem.title}' oleh ${resolvedItem.author} (${resolvedItem.pages} hal).")
        is Journal -> println("Jurnal Terverifikasi: '${resolvedItem.title}', Penerbit: ${resolvedItem.publisher}.")
        is DVD -> println("DVD Terverifikasi: '${resolvedItem.title}', Sutradara: ${resolvedItem.director}.")
    }

    // Safe Cast (as?) tanpa risiko ClassCastException
    val bookCast: Book? = resolvedItem as? Book
    val dvdCast: DVD? = resolvedItem as? DVD
    println("Safe Cast (as? Book): ${bookCast?.title ?: "Gagal Cast"}")
    println("Safe Cast (as? DVD) : ${dvdCast?.title ?: "Bukan DVD (Safe Null)"}")

    // Enkapsulasi: verifikasi akses properti private via getter resmi
    println("\n=== ENKAPSULASI DATA ANGGOTA ===")
    println("ID: ${memberAhmad?.id} | Nama: ${memberAhmad?.name} | Email: ${memberAhmad?.getEmail() ?: "-"} | Telepon: ${memberAhmad?.getPhone() ?: "-"}")

    // Laporan statistik sistem perpustakaan
    println("\n=== LAPORAN STATISTIK AKHIR ===")
    library.displayReport()
}
