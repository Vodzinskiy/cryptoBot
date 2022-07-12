import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TelegramUI extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "@CryptoCoinCheckerBot";
    }

    @Override
    public String getBotToken() {
        return "5535586753:AAG5d2DoTHTRFZXv1K1G784iszDqL9HohRY";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            Message message = update.getMessage();
            String chatId = message.getChatId().toString();

            if (!message.getText().startsWith("/")){
                getCryptoPrice(message,chatId);
            }
            else {
                switch (message.getText()){
                    case ("/start"):
                        start(chatId);
                        break;
                    case  ("/help"):
                        help(chatId);
                        break;
                    default:
                        SendMessage outMess = new SendMessage();
                        outMess.setChatId(chatId);
                        outMess.setText("Unrecognized command. Try /help");
                        try {
                            execute(outMess);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                }
            }
        }
    }
    private void start(String chatId)  {
        SendMessage outMess = new SendMessage();
        outMess.setChatId(chatId);
        outMess.setText("Hello! I will help you monitor all your cryptocurrencies to see everything I can write the command /help");
        try {
            execute(outMess);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void help(String chatId){
        SendMessage outMess = new SendMessage();
        outMess.setChatId(chatId);
        outMess.setText("If you want to know the current value of the currency, write its name in the chat");
        try {
            execute(outMess);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCryptoPrice(Message message,String chatId){
        CryptoPrice cryptoPrice = new CryptoPrice(message.getText());
        SendMessage outMess = new SendMessage();
        outMess.setChatId(chatId);
        if (cryptoPrice.prise()!=null){
            outMess.setText(cryptoPrice.prise());
        }
        else {
            outMess.setText("Sorry, I can't find a cryptocurrency with that name");
        }
        try {
            execute(outMess);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
