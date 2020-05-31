package cn.whdreamblog.myapplication;

import org.junit.Test;

import cn.whdreamblog.myapplication.data.NewWorkManger;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
       boolean a =  "GET".equalsIgnoreCase("get");
        System.out.println(a);
        NewWorkManger demo = new NewWorkManger();
        try {
            String url1 = demo.generateGetSuggestionURL("beijing");
            System.out.println("alarmUrl:"+url1);
            String url = demo.generateGetDiaryWeatherURL(
                    "shanghai"
            );
            System.out.println("URL:" + url);
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
    }
}