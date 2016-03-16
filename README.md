# CcCharts
一个自定义图表「因为公司项目需要，网上那些优秀的开源图表无法使用，所以自己定义了一个，索性一起学习了github发上来，多多鼓励自己」

#怎么使用
CcRadarChart：雷达图-六芒星图

使用gradle：
build.gradle添加「Project」：
allprojects {
    repositories {
        jcenter()
        maven {
            url 'https://dl.bintray.com/yz253233154/maven/'
        }
    }
}

build.gradle添加「app」：
compile 'com.oom.cccharts:cc-charts:1.0.0'


在xml中添加
<com.oom.cccharts.view.widget.CcRadarChart
    android:layout_width="match_parent"
    android:layout_height="300dp"/>

最好设置一个合理的高度，保证图表正常显示.

