package util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import util.ActionByClassName;
import util.ActionByCss;
import util.ActionById;
import util.ActionByLinkText;
import util.ActionByName;
import util.ActionByPartialLinkText;
import util.ActionByTagName;
import util.ActionByXpath;

import org.openqa.selenium.WebDriver;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by skumar on 2/14/2017.
 */
public class ActionFor {
    /**
     * This method is use to work with elements that will keep on changing and there will be no way to predict
     * @param driver
     * @param array
     * Action that can be used are clear
        type
        sendKeys
        selectByText
        selectByValue
        selectByIndex
        click
        assertVisible
        assertNotVisible
        assertTextEqualsTrue
        assertTextContainstrue
        assertEqualsText
        assertNotEqualsText
     * DataElement sample - index and value may be included or not depending on the action.
     * [
     * {"locator":"id","attribute":"question","index":"2","action":"sendKeys","value":"please enter text","timeoutInSecs":"30"},
     * {"locator":"name","attribute":"question","index":"2","action":"sendKeys","value":"please enter text","timeoutInSecs":"30"}
     *]
     *
     * **/
    public static void action(WebDriver driver,JsonArray array)
    {
        for(int i=0;i<array.size();i++)
        {
                int counter = 0;
                String locator = "";
                String attribute = "";
                String action = "";
                String value = "";
                int index = -1;
                int timeoutInSecs = 30;
                JsonObject obj = array.get(i).getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> infoSet = obj.entrySet();
                Iterator<Map.Entry<String, JsonElement>> iter = infoSet.iterator();
                while ( iter.hasNext() ) {
                Map.Entry<String, JsonElement> infoElement = iter.next();
                String elementName = infoElement.getKey();
                JsonElement elementValue = infoElement.getValue();
                switch(elementName)
                {
                    case "locator":
                        locator = elementValue.getAsString();
                        break;
                    case "attribute":
                        attribute = elementValue.getAsString();
                        break;
                    case "index":
                        index = Integer.parseInt(elementValue.getAsString());
                        break;
                    case "action":
                        attribute = elementValue.getAsString();
                        break;
                    case "value":
                        value = elementValue.getAsString();
                    case "timeoutInSecs":
                        timeoutInSecs = Integer.parseInt(elementValue.getAsString());
                        switch (locator.toLowerCase().trim())
                        {
                            case "classname":
                                ActionByClassName.action(driver,attribute,action,value,index,timeoutInSecs);
                                break;
                            case "css":
                                ActionByCss.action(driver,attribute,action,value,index,timeoutInSecs);
                                break;
                            case "id":
                                ActionById.action(driver,attribute,action,value,index,timeoutInSecs);
                                break;
                            case "linktext":
                                ActionByLinkText.action(driver,attribute,action,value,index,timeoutInSecs);
                                break;
                            case "name":
                                ActionByName.action(driver,attribute,action,value,index,timeoutInSecs);
                                break;
                            case "partiallinktext":
                                ActionByPartialLinkText.action(driver,attribute,action,value,index,timeoutInSecs);
                                break;
                            case "tagname":
                                ActionByTagName.action(driver,attribute,action,value,index,timeoutInSecs);
                                break;
                            case "xpath":
                                ActionByXpath.action(driver,attribute,action,value,index,timeoutInSecs);
                                break;
                            default:break;
                        }
                        break;
                    default:break;
                }
            }

        }
    }
}
