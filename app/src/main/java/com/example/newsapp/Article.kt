data class Article(
    val title: String?,
    val source: String?,
    val author: String?,
    val description: String?,
    val imageUrl: String?,
    val url: String?
) {
    val urlToImage: Any
        get() {
            TODO()
        }
}
