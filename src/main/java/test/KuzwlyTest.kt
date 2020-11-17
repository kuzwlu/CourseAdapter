package main.java.test

import main.java.parser.KuzwluParser
import java.io.File

fun main() {
    // 示例中用了相对路径，Windows 下可能需要修改
    // 建议从项目外引用 html 文件
    // 提交时一定不要上传 html 文件，涉及隐私问题
    val file = File("/Users/kuzwlu/Desktop/KCB.html")
    val parser = KuzwluParser(file.readText())
    parser.saveCourse()
}
