package juniormourao.rickandmorty.core

import android.annotation.SuppressLint
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.google.android.material.snackbar.Snackbar
import juniormourao.rickandmorty.R

object Extensions {
    @SuppressLint("NewApi")
    fun View.showSnackBar(message: String, action: (Snackbar.() -> Unit)? = null) {
        val snackbar = Snackbar.make(
            this,
            Html.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY),
            Snackbar.LENGTH_LONG
        ).setAction(
            this.context.getString(R.string.close)
        ) {}.setActionTextColor(ContextCompat.getColor(this.context, R.color.wine))

        action?.let { snackbar.it() }
        val view = snackbar.view
        val textView = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.maxLines = 2
        snackbar.show()
    }

    fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
    }
}