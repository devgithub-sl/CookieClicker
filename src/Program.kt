import java.awt.Color
import java.awt.Font
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.Timer
import kotlin.properties.Delegates

fun main() {
    CookieMain()
}

class CookieMain {
    var cookieCounter: Int = 0
    val cHandler = CookieHandler()
    var perSecond: Double = 0.0
    var timerOn: Boolean = false
    var cursorNumber = 0
    var cursorPrice = 10
    var mHandler = MouseHandler()
    var grandpaUnlocked = false
    var grandpaNumber = 0
    var grandpaPrice = 100

    var timerSpeed by Delegates.notNull<Int>()

    lateinit var counterLabel: JLabel
    lateinit var perSecLabel: JLabel
    lateinit var font1: Font
    lateinit var font2: Font
    lateinit var button1: JButton
    lateinit var button2: JButton
    lateinit var button3: JButton
    lateinit var button4: JButton
    lateinit var timer: Timer
    lateinit var messageText: JTextArea

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
        cookieButton.addActionListener(cHandler)
        cookieButton.actionCommand = "Cookie"
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

        val itemPanel = JPanel()
        itemPanel.setBounds(500, 170, 250, 250)
        itemPanel.background = Color.BLACK
        itemPanel.layout = GridLayout(4, 1)
        window.add(itemPanel)

        button1 = JButton("Cursor")
        button1.font = font1
        button1.isFocusPainted = false
        button1.addActionListener(cHandler)
        button1.actionCommand = "Cursor"
        button1.addMouseListener(mHandler)
        itemPanel.add(button1)

        button2 = JButton("?")
        button2.font = font1
        button2.isFocusPainted = false
        button2.addActionListener(cHandler)
        button2.actionCommand = "Grandpa"
        button2.addMouseListener(mHandler)
        itemPanel.add(button2)

        button3 = JButton("?")
        button3.font = font1
        button3.isFocusPainted = false
        button3.addActionListener(cHandler)
        button3.actionCommand = "Cursor"
        button3.addMouseListener(mHandler)
        itemPanel.add(button3)

        button4 = JButton("?")
        button4.font = font1
        button4.isFocusPainted = false
        button4.addActionListener(cHandler)
        button4.actionCommand = "Cursor"
        button4.addMouseListener(mHandler)
        itemPanel.add(button4)

        val messagePanel = JPanel()
        messagePanel.setBounds(500, 70, 250, 150)
        messagePanel.background = Color.BLACK
        window.add(messagePanel)

        messageText = JTextArea()
        messageText.setBounds(500, 70, 250, 150)
        messageText.foreground = Color.WHITE
        messageText.background = Color.BLACK
        messageText.font = font2
        messageText.lineWrap = true
        messageText.wrapStyleWord = true
        messageText.isEditable = false
        messagePanel.add(messageText)

        window.isVisible = true
    }

    fun setTimer() {
        timer = Timer(timerSpeed) { _ ->
            cookieCounter++
            counterLabel.text = "$cookieCounter cookies"

            if (!grandpaUnlocked && cookieCounter >= 100) {
                grandpaUnlocked = true
                button2.text = "Grandpa ($grandpaNumber)"
                button2.actionCommand = "Grandpa" 
            }
        }
    }

    fun timerUpdate() {
        if (!timerOn) {
            timerOn = true
        } else {
            timer.stop()
        }

        val speed: Double = 1 / perSecond * 1000
        timerSpeed = Math.round(speed).toInt()

        val s: String = String.format("%.1f", perSecond)
        perSecLabel.text = "per second: $s"

        setTimer()
        timer.start()
    }

    inner class CookieHandler : ActionListener {
        override fun actionPerformed(e: ActionEvent?) {
            val action = e?.actionCommand

            when (action) {
                "Cookie" -> {
                    cookieCounter++
                    counterLabel.text = "$cookieCounter cookies"
                }

                "Cursor" -> {
                    if (cookieCounter >= cursorPrice) {
                        cookieCounter -= cursorPrice
                        cursorPrice += 5
                        counterLabel.text = "$cookieCounter cookies"

                        cursorNumber++
                        button1.text = "Cursor ($cursorNumber)"
                        messageText.text = "Cursor\n[price: $cursorPrice]\nAutoclicks once ever 10 seconds"
                        perSecond += 0.1
                        timerUpdate()
                    } else {
                        messageText.text = "You need more cookies"
                    }
                }

                "Grandpa" -> {
                    if (cookieCounter >= grandpaPrice) {
                        cookieCounter -= grandpaPrice
                        grandpaPrice += 50
                        counterLabel.text = "$cookieCounter cookies"

                        grandpaNumber++
                        button2.text = "Grandpa ($grandpaNumber)"
                        messageText.text = "Grandpa\n[price: $grandpaPrice]\nEach grandpa produces 1 cookie per second"
                        perSecond += 1.0
                        timerUpdate()
                    } else {
                        messageText.text = "You need more cookies"
                    }
                }
            }
        }
    }

    inner class MouseHandler : MouseListener {
        override fun mouseClicked(e: MouseEvent?) {}
        override fun mousePressed(e: MouseEvent?) {}
        override fun mouseReleased(e: MouseEvent?) {}

        override fun mouseEntered(e: MouseEvent?) {
            val button: JButton = e?.source as JButton

            when (button) {
                button1 -> {
                    messageText.text = "Cursor\n[price: $cursorPrice]\nAutoclicks once ever 10 seconds"
                }

                button2 -> {
                    messageText.text = if (!grandpaUnlocked) {
                        "This item is currently locked"
                    } else {
                        "Grandpa\n[price: $grandpaPrice]\nEach grandpa produces 1 cookie per second"
                    }
                }

                button3, button4 -> {
                    messageText.text = "This item is currently locked"
                }
            }
        }

        override fun mouseExited(e: MouseEvent?) {
            messageText.text = ""
        }
    }
}
