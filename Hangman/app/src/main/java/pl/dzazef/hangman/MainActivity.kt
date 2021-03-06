package pl.dzazef.hangman

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


enum class State {
    LOST, WON, UNDEFINED
}

fun Array<String>.random(): String = if (size > 0) get(Random().nextInt(size)) else ""

fun String.addSpaces(): String {
    var out = ""
    for (i in this) {
        out = "$out$i "
    }
    return out
}

class WordManager(private val context: Context) {
    var guessedLetters = mutableListOf<Char>()
    var currentWord : String = ""
    var dashedWord : String = ""
    var old = Integer.MAX_VALUE
    var new = 0

    fun randomWord(): String {
        currentWord = context.resources.getStringArray(R.array.words).random().toUpperCase().addSpaces()
        Log.d("INFO1", currentWord)
        return currentWord
    }

    fun wordToDash(): String {
        var newWord = currentWord
        for (l in newWord) {
            if (l !in guessedLetters && l!=' ' && l!='＿') {
                newWord = newWord.replace(l.toString(), "＿")
            }
        }
        dashedWord = newWord
        new = dashedWord.filter { it == '＿' }.length
        Log.d("INFO1", dashedWord)
        return dashedWord
    }

    fun addLetter(letter: Char) {
        guessedLetters.add(letter)
    }

    fun change(): Boolean {
        val temp = old
        old = new
        return (new<temp)
    }
}

class MainActivity : AppCompatActivity() {
    private val wordManager by lazy {
        WordManager(this)
    }
    var whichImage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordManager.randomWord()
        textView.text = wordManager.wordToDash()
        wordManager.change()
    }

    fun onClick(view: View) {
        val button = findViewById<Button>(view.id)
        when (button) {
            buttonQ -> wordManager.addLetter('Q')
            buttonW -> wordManager.addLetter('W')
            buttonE -> wordManager.addLetter('E')
            buttonR -> wordManager.addLetter('R')
            buttonT -> wordManager.addLetter('T')
            buttonY -> wordManager.addLetter('Y')
            buttonU -> wordManager.addLetter('U')
            buttonI -> wordManager.addLetter('I')
            buttonO -> wordManager.addLetter('O')
            buttonP -> wordManager.addLetter('P')
            buttonA -> wordManager.addLetter('A')
            buttonS -> wordManager.addLetter('S')
            buttonD -> wordManager.addLetter('D')
            buttonF -> wordManager.addLetter('F')
            buttonG -> wordManager.addLetter('G')
            buttonH -> wordManager.addLetter('H')
            buttonJ -> wordManager.addLetter('J')
            buttonK -> wordManager.addLetter('K')
            buttonL -> wordManager.addLetter('L')
            buttonZ -> wordManager.addLetter('Z')
            buttonX -> wordManager.addLetter('X')
            buttonC -> wordManager.addLetter('C')
            buttonV -> wordManager.addLetter('V')
            buttonB -> wordManager.addLetter('B')
            buttonN -> wordManager.addLetter('N')
            buttonM -> wordManager.addLetter('M')
        }
        button.visibility = View.INVISIBLE

        textView.text = wordManager.wordToDash()
        if (!wordManager.change()) whichImage++
        when (checkHangman()) {
            State.LOST -> {
                Toast.makeText(this, "YOU LOST! :( Word: ${wordManager.currentWord}", Toast.LENGTH_SHORT).show()
                reset()
            }
            State.WON -> {
                Toast.makeText(this, "YOU WON :)", Toast.LENGTH_SHORT).show()
                reset()
            }
            State.UNDEFINED -> Unit
        }
    }

    fun reset() {
        Log.d("INFO1", "Generating new word...")
        wordManager.guessedLetters.clear()
        whichImage = 0
        wordManager.randomWord()
        textView.text = wordManager.wordToDash()
        wordManager.change()
        checkHangman()
        for (i in "QWERTYUIOPASDFGHJKLZXCVBNM") {
            findViewById<Button>(resources.getIdentifier("button$i", "id", this.packageName)).visibility = View.VISIBLE
        }
    }

    fun checkHangman(): State {
        when (whichImage) {
            0 -> imageView.setImageResource(0)
            1 -> imageView.setImageResource(R.drawable.hangman1)
            2 -> imageView.setImageResource(R.drawable.hangman2)
            3 -> imageView.setImageResource(R.drawable.hangman3)
            4 -> imageView.setImageResource(R.drawable.hangman4)
            5 -> imageView.setImageResource(R.drawable.hangman5)
            6 -> imageView.setImageResource(R.drawable.hangman6)
            7 -> imageView.setImageResource(R.drawable.hangman7)
            8 -> imageView.setImageResource(R.drawable.hangman8)
            9 -> imageView.setImageResource(R.drawable.hangman9)
            10 -> return State.LOST
        }
        if (wordManager.currentWord == wordManager.dashedWord) {
            return State.WON
        }

        return State.UNDEFINED
    }
}
