import java.awt.Color
import java.awt.Font
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

fun main() {
    CookieMain()
}

class CookieMain {
    var cookieCounter: Int = 0
    lateinit var counterLabel: JLabel
    lateinit var perSecLabel: JLabel
    lateinit var font1: Font
    lateinit var font2: Font

    constructor() {
        createFont()
        createUI()
    }

    fun createFont() {
        font1 = Font("Comic Sans MS", Font.PLAIN, 32)
        font2 = Font("Comic Sans MS", Font.PLAIN, 15)
    }

    fun createUI() {
        val window = JFrame()
        window.setSize(800, 600)
        window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        window.contentPane.background = Color.BLACK
        window.title = "Cookie Clicker: Kotlin"
        window.layout = null

        val cookiePanel = JPanel()
        cookiePanel.setBounds(100, 200, 360, 360)
        cookiePanel.background = Color.BLACK
        window.add(cookiePanel)

        val cookie = ImageIcon("src/cookie.png")
        val cookieButton = JButton()
        cookieButton.background = Color.BLACK
        cookieButton.isFocusPainted = false
        cookieButton.border = null
        cookieButton.icon = cookie
        cookieButton.addActionListener(CookieHandler())
        cookiePanel.add(cookieButton)

        val counterPanel = JPanel()
        counterPanel.setBounds(75, 75, 200, 100)
        counterPanel.background = Color.BLACK
        counterPanel.layout = GridLayout(2, 1)
        window.add(counterPanel)

        counterLabel = JLabel("$cookieCounter cookies")
        counterLabel.foreground = Color.WHITE
        counterLabel.font = font1
        counterPanel.add(counterLabel)

        perSecLabel = JLabel()
        perSecLabel.foreground = Color.WHITE
        perSecLabel.font = font2
        counterPanel.add(perSecLabel)

        window.isVisible = true
    }

    inner class CookieHandler : ActionListener {
        override fun actionPerformed(e: ActionEvent?) {
            cookieCounter++
            counterLabel.text = "$cookieCounter cookies"
        }
    }
}
