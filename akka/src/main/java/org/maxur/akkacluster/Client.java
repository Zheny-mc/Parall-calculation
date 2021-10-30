package org.maxur.akkacluster;


import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.perf4j.log4j.Log4JStopWatch;

import static akka.actor.ActorRef.noSender;

/**
 * @author Maxim Yunusov
 * @version 1.0 14.09.2014
 */
public class Client extends UntypedActor {

    private LoggingAdapter logger = Logging.getLogger(context().system(), this);

    private ActorSelection worker;

    public static void main(String[] args) throws Exception {
        startSystem();
    }

    private static void startSystem() {
        final Config config = ConfigFactory.load().getConfig("client");
        ActorSystem system = ActorSystem.create("ClientSystem", config);
        system.actorOf(Props.create(Client.class));
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            String mess = String.format("%s", message);
            String[] type = mess.split("%");
            if (type[0].equals("work")) {
                Record record = SerialObj.deSerial(type[1]);
            	logger.info(record.toString());
            }
            //Log4JStopWatch stopWatch = new Log4JStopWatch("Client");
            //logger.info((String)message);
            //stopWatch.stop();
        }
    }

    @Override
    public void preStart() {
        logger.info("Start Client");
        final String path = "akka.tcp://WorkerSystem@127.0.0.1:2550/user/worker";
        worker = context().system().actorSelection(path);
        run();
    }

    @Override
    public void postStop() {
        //worker.tell(PoisonPill.getInstance(), noSender());
        context().system().shutdown();
        logger.info("Stop Client");
    }

    private void run() {
        try {
            for (int i = 0; i < 1; i++) {
                //worker.tell(sale(), self());
                //worker.tell(buy(), self());
                worker.tell(update(), self());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String sale() {
        return SerialObj.serial(Record.create(true));
    }

    private String buy() {
        return SerialObj.serial(Record.create(false));
    }

    private String update() {
        return "update";
    }
}
