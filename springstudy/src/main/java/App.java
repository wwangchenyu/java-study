import com.asiainfo.entity.Cinema;
import com.asiainfo.entity.Ticket;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        Ticket ticket1 = (Ticket) applicationContext.getBean("ticket");
        Ticket ticket2 = (Ticket) applicationContext.getBean("ticket");
        System.out.println(ticket1.checkTicket());
        System.out.println(ticket2.checkTicket());
        System.out.println(ticket1 == ticket2);

        Cinema cinema = (Cinema) applicationContext.getBean("cinema");
        cinema.infomation();
    }

}
