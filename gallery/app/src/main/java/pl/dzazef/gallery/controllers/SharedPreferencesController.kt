package pl.dzazef.gallery.controllers

import android.content.Context
import android.support.v7.app.AppCompatActivity
import pl.dzazef.gallery.PREF_NAME
import pl.dzazef.gallery.PREF_DESCRIPTION
import pl.dzazef.gallery.PREF_RATING
import pl.dzazef.gallery.data.Item

class SharedPreferencesController(private val appCompatActivity: AppCompatActivity) {
    fun saveItemState(item: Item?) {
        if (item!=null) {
            val sharedPref = appCompatActivity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            if (item.description != null) editor.putString(PREF_DESCRIPTION+item.path, item.description)
            editor.putFloat(PREF_RATING + item.path, item.rating)
            editor.apply()
        }
    }

    fun restoreItemState(item: Item) {
        val sharedPref = appCompatActivity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        item.description = sharedPref.getString(PREF_DESCRIPTION+item.path, null)
        item.rating = sharedPref.getFloat(PREF_RATING+item.path, 0f)
    }
}
