//package com.example.quizapp.feature.data.repository.impl
//
//
//import android.util.Log
//import androidx.lifecycle.viewModelScope
//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.PagingData
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.google.firebase.firestore.DocumentSnapshot
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.tasks.await
//import javax.inject.Inject
//
//class CommentPagingSource(
//    private val textId: String,
//) : PagingSource<DocumentSnapshot, Comment>() {
//    private var fireStoreDatabase = FirebaseFirestore.getInstance()
//
//    override suspend fun load(params: LoadParams<DocumentSnapshot>): LoadResult<DocumentSnapshot, Comment> {
//        return try {
//            // Pobierz pierwsze 10 lub następne 10 komentarzy na podstawie parametrów
//            val currentPage = params.key?.let {
//                fireStoreDatabase
//                    .collection("Comments")
//                    .whereEqualTo("textId", textId)
//                    .startAfter(it)
//                    .limit(10L)
//                    .get()
//                    .await()
//            } ?: fireStoreDatabase
//                .collection("Comments")
//                .whereEqualTo("textId", textId)
//                .limit(10L)
//                .get()
//                .await()
//
//            val comments = currentPage.documents.mapNotNull { documentToComment(it) }
//
//            val lastVisible = currentPage.documents.lastOrNull()
//
//            return LoadResult.Page(
//                data = comments,
//                prevKey = null, // biblioteka Paginacja nie obsługuje domyślnie paginacji wstecznej
//                nextKey = lastVisible // jako klucz dla następnego wywołania `load()`
//            )
//        }catch (e: Exception) {
//            Log.e("CommentPagingSource", "Error loading comments", e)
//            LoadResult.Error(e)
//
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<DocumentSnapshot, Comment>): DocumentSnapshot? {
//        return null
//    }
//    fun documentToComment(doc: DocumentSnapshot): Comment? {
//        val textId = doc.getString("textId") ?: return null
//        val userId = doc.getString("userId") ?: return null
//        val commentId = doc.getString("commentId") ?: return null
//        val timestamp = doc.getTimestamp("timestamp") ?: return null
//        val content = doc.getString("content") ?: return null
//        val author = doc.getString("author") ?: return null
//
//        return Comment(textId, userId, commentId, timestamp, content, author)
//    }
//}
//
//
//
//class GetComments
//@Inject constructor(
//) {
//    operator fun invoke(commentPagingSource: CommentPagingSource): Flow<PagingData<Comment>> {
//
//        return Pager(PagingConfig(pageSize = 10)) {
//            commentPagingSource
//        }.flow
//    }
//
//}
//init {
//    savedStateHandle.get<String>("textId")?.let { textId ->
//        this.currentTextId = textId
//        try {
//            val commentPagingSource = CommentPagingSource(currentTextId)
//            commentsFlow = getComments(commentPagingSource).cachedIn(viewModelScope)
//        } catch (e: Exception) {
//            Log.e("commentviewmodelinit", "Error", e)
//        }
//    }
//}