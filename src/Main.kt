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
import javax.swing.Timer
import kotlin.properties.Delegates

fun main() {
    CookieMain()
}

class CookieMain {
    var cookieCounter: Int = 0
    val cHandler = CookieHandler()
    var perSecond : Double = 0.0
    var timerOn : Boolean = false
    var timerSpeed by Delegates.notNull<Int>()


    lateinit var counterLabel: JLabel
    lateinit var perSecLabel: JLabel
    lateinit var font1: Font
    lateinit var font2: Font
    lateinit var button1 : JButton
    lateinit var button2 : JButton
    lateinit var button3 : JButton
    lateinit var button4 : JButton
    lateinit var timer : Timer

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
        itemPanel.setBounds(500, 170 , 250, 250)
        itemPanel.background = Color.BLACK
        itemPanel.layout = GridLayout(4,1)
        window.add(itemPanel)

        button1 = JButton("Cursor")
        button1.font = font1
        button1.isFocusPainted = false
        button1.addActionListener(cHandler)
        button1.actionCommand = "Cursor"
        itemPanel.add(button1)

        button2 = JButton("?")
        button2.font = font1
        button2.isFocusPainted = false
        button2.addActionListener(cHandler)
        button2.actionCommand = "Cursor"
        itemPanel.add(button2)

        button3 = JButton("?")
        button3.font = font1
        button3.isFocusPainted = false
        button3.addActionListener(cHandler)
        button3.actionCommand = "Cursor"
        itemPanel.add(button3)

        button4 = JButton("?")
        button4.font = font1
        button4.isFocusPainted = false
        button4.addActionListener(cHandler)
        button4.actionCommand = "Cursor"
        itemPanel.add(button4)

        window.isVisible = true
    }

    fun setTimer() {
        timer = Timer(timerSpeed, object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                cookieCounter++
                counterLabel.text = "$cookieCounter cookies"
            }
        })
    }

    fun timerUpdate() {
        if(!timerOn) {
            timerOn = true
        } else {
            timer.stop()
        }

        val speed : Double = 1 / perSecond * 1000
        timerSpeed = Math.round(speed).toInt()

        val s : String = String.format("%.2f", perSecond)
        perSecLabel.text = "per second: $s"

        setTimer()
        timer.start()
    }

    inner class CookieHandler : ActionListener {
        override fun actionPerformed(e: ActionEvent?) {
            val action = e?.actionCommand

            when(action) {
               "Cookie" -> {
                   cookieCounter++
                   counterLabel.text = "$cookieCounter cookies"
               }
                "Cursor" -> {
                    perSecond += 0.1
                    timerUpdate()
                }

            }
        }
    }
}
