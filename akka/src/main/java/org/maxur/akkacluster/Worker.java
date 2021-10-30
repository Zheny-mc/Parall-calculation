package org.maxur.akkacluster;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static akka.actor.ActorRef.noSender;
import static java.lang.String.format;

/**
 * @author Maxim Yunusov
 * @version 1.0 14.09.2014
 */
public class Worker extends UntypedActor {

    private static List<Record> listProduct;

    private LoggingAdapter logger = Logging.getLogger(context().system(), this);

    private ActorRef repository;

    public static void main(String[] args) throws Exception {
        final Config config = ConfigFactory.load().getConfig("worker");
        ActorSystem system = ActorSystem.create("WorkerSystem", config);
        system.actorOf(Props.create(Worker.class), "worker");

        listProduct = new ArrayList();
        for (int i = 0; i < 3; i++) {
        	listProduct.add(Record.create(true));
		}
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            String response = String.format("%s", message);
            //---------------------start work -------------------
            if (response.equals(message)) {
                if (listProduct.size() > 0){
                    for (Record i: listProduct){
                        sender().tell("work%" + SerialObj.serial(i), ActorRef.noSender());
                    }
                } else {
                    sender().tell("work%empty", ActorRef.noSender());
                }

            } else {
                Record record = SerialObj.deSerial(response);

                if (record.getView()) {
                    listProduct.add(record);
                } else {
                    if (listProduct.size() > 0) {
                        listProduct.remove(listProduct.size() - 1);
                    }
                }

                this.logger.info("" + listProduct.size());

                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //---------------------end work----------------------------------

                sender().tell( "Good " + listProduct.size(), ActorRef.noSender());
                //repository.tell(response, sender());
            }
        }
    }

    @Override
    public void preStart() throws Exception {
        logger.info("Start Worker");
        //repository = context().actorOf(Props.create(Repository.class));
    }

    @Override
    public void postStop() throws Exception {
        //repository.tell(PoisonPill.getInstance(), noSender());
        logger.info("Stop Worker");
    }

}
