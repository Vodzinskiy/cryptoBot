import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class CryptoPrice {
    String crypto;
    public CryptoPrice(String crypto) {
        this.crypto = crypto.toLowerCase().replace(" ","-");
    }

    public String prise(){
        try {
            Document doc = Jsoup.connect("https://coinmarketcap.com/currencies/"+crypto+"/").get();
            Elements body = doc.getElementsByClass("priceValue");
            return body.text().replace("$","");
        }catch (Exception e){
            return null;
        }
    }
}
