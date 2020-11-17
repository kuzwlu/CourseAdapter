package main.java.parser

import bean.Course
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import parser.ImportBean
import parser.Parser
import java.lang.Exception

class KuzwluParser(source: String):Parser(source){
    override fun generateCourseList(): List<Course> {
        var result = arrayListOf<Course>()
        val doc = Jsoup.parse(source)
        val manualArrangeCourseTable = doc.getElementById("manualArrangeCourseTable")
        val tbody = manualArrangeCourseTable.getElementsByTag("tbody") //.getElementsByTag("tbody")
        val trs = tbody[0].getElementsByTag("tr")
        for(tr in trs) {
            val tds = tr.getElementsByTag("td")
            var node :Int =0
            for (td in tds) {
                var tdParser = tdParser(tds, td, node)
                if (tdParser != null) {
                    result.add(tdParser)
                }
                node++
            }
        }
        return result
    }

    private fun tdParser(tds : Elements,td: Element,node:Int) : Course? {
        var courseSource = td.text().trim()
        if (!courseSource.isEmpty() && courseSource != null && !node.equals(0)) {
            var c_name = courseSource.split("(")[0].replace(" ", "")
            var c_day = node
            var c_room = courseSource.split(",")[1].replace(" ", "")
                .substring(0, courseSource.split(",")[1].replace(" ", "").length - 1)
            var c_teacher = courseSource.split("(")[2].replace(" ", "")
                .substring(0, courseSource.split("(")[2].replace(" ", "").length - 1)
            var c_startNode_string = tds[0].text().replace("\n", "")
            var c_startNode = Common.containNodeInt(c_startNode_string)
            var c_endNode = c_startNode + 1
            if (c_startNode == 11) {
                c_endNode = c_startNode
            }
            var c_weeks = courseSource.split(",")[0]
                .split("(")[3]
                .replace(" ", "")
            var begin = c_weeks.split("-")[0]
            var end = c_weeks.split("-")[1]
            var c_startweek: Int;
            var c_endWeek: Int;
            var type: Int;
            if (begin.substring(0, 1).equals("双")) {
                c_startweek = begin.substring(1, 2).toInt()
                c_endWeek = end.toInt()
                type = 2
            } else if (begin.substring(0, 1).equals("单")) {
                c_startweek = begin.substring(1, 2).toInt()
                c_endWeek = end.toInt()
                type = 1
            } else {
                c_startweek = begin.toInt()
                c_endWeek = end.toInt()
                type = 0
            }
            return Course(
                c_name,
                c_day,
                c_room,
                c_teacher,
                c_startNode,
                c_endNode,
                c_startweek,
                c_endWeek, type
            )
        }else{
            return null
        }
    }

}
