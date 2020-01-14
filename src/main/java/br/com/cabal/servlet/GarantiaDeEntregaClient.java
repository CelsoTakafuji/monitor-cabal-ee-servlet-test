package br.com.cabal.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import br.com.cabal.monitor.BeanMensagem;

@WebServlet("/EnviaMensagemParaFila")
public class GarantiaDeEntregaClient extends HttpServlet {
	
	private static final long serialVersionUID = 1771591354313261635L;

	private final Logger logger = Logger.getLogger("GarantiaDeEntregaClient");
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			PrintWriter out = resp.getWriter();
	
			out.println("<html> ");
			out.println("	<head> ");
			out.println("	    <!-- Required meta tags --> ");
			out.println("		<meta charset=\"utf-8\"> ");
			out.println("		<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\"> ");
			out.println(" ");
			out.println("		<!-- Bootstrap CSS --> ");
			out.println("		<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\"> ");
			out.println(" ");
			out.println("		<title>Send Monitor Cabal EE</title> ");
			out.println("	</head> ");
			out.println("	<body style=\"background:lightblue; padding-left:50px\"> ");
			out.println("		<h1 class=\"text-center\">Enviar mensagens para Monitor Cabal EE</h1> ");
			out.println(" ");
			out.println("		<br /> ");
			out.println("		<br /> ");
			out.println(" ");
			out.println("		<form method=\"post\" action=\"EnviaMensagemParaFila\"> ");
			out.println(" ");
			out.println("			<div class=\"form-group row\"> "); // NOSONAR
			out.println("				<label class=\"ml-5 col-sm-2 col-form-label\">Mensagem Iso In:</label> ");
			out.println("				<textarea class=\"col-sm-8\" id=\"msgIn\" name=\"msgIn\" rows=\"3\">0200FEFB4601A8E1E20A000000000004000416512707003774083400000000000000251500000000251500000000251510211805256100000061000000147827150525102110211020546205100209998653213109000000532375127070037740834=230320142000000000003932858     WY456527044906722      CEMAR PAES E CONVENIEN BRASILIA      BRA001R9869869862049F2608C9D8411A5C9DBD839F2701809F10120010A50003020000000000000000000000FF9F37044B89F3A39F36020251950580000080009A031810219C01009F02060000000025155F2A020986820218009F1A0200769F34034103029F3303E0D0C89F350122026000000000030107672015535  012MS25791410070070803MSI050006P7E009HISFNX                                   </textarea> ");
			out.println("			</div> ");                         // NOSONAR
			out.println(" ");
			out.println("			<div class=\"form-group row\"> ");
			out.println("				<label class=\"ml-5 col-sm-2 col-form-label\">Mensagem Iso Out:</label> ");
			out.println("				<textarea class=\"col-sm-8\" id=\"msgOut\" name=\"msgOut\" rows=\"3\">0210FABA00018E81C202000000000000000416512707003774083400000000000000251500000000251510211805256100000014782715052510211021099986532131090000005323932858     17742400WY456527008R740350G986986024910AB20DE119CA35FEB90312012MS2579141007050006P7E009HISFNX                                   </textarea> ");
			out.println("			</div> ");
			out.println(" ");
			out.println("			<div class=\"form-group row\"> ");
			out.println("				<label class=\"ml-5 col-sm-2 col-form-label\">Fila:</label> ");
			out.println("				<select class=\"col-sm-8\" name=\"queue\"> ");
			out.println("					<option value=\"MONITOR_CABAL_EE_JPOS_QUEUE\">jPos</option> ");
			out.println("					<option value=\"MONITOR_CABAL_EE_VOUCHER_QUEUE\">Convênio</option> ");
			out.println("					<option value=\"MONITOR_CABAL_EE_CREDIT_QUEUE\">Crédito</option> ");
			out.println("					<option value=\"MONITOR_CABAL_EE_DEBIT_QUEUE\">Débito</option> ");
			out.println("				</select> ");
			out.println("			</div> ");
			out.println("  ");
			out.println("			<div class=\"form-group row\"> ");
			out.println("				<label class=\"ml-5 col-sm-2 col-form-label\">Rede:</label> ");
			out.println("				<select class=\"col-sm-8\" name=\"rede\"> ");
			out.println("					<option value=\"MASTER\">MASTER</option> ");
			out.println("					<option value=\"MAESTR\">MAESTR</option> ");
			out.println("					<!--<option value=\"VISACR\">VISACR</option> -->");
			out.println("					<!--<option value=\"VISADB\">VISADB</option> -->");
			out.println("					<option value=\"VISANT\">VISANT</option> ");
			out.println("					<option value=\"CABINT\">CABINT</option> ");
			out.println("					<option value=\"WEDCAB\">WEBCAB</option> ");
			out.println("				</select> ");
			out.println("			</div> ");
			out.println(" ");
			out.println("			<br /> ");
			out.println("			<br /> ");
			out.println(" ");
			out.println("			<div class=\"form-group row\"> ");
			out.println("				<div class=\"ml-5 col-sm-8\"> ");
			out.println("					<button type=\"submit\" class=\"btn btn-primary\">Enviar mensagem</button> ");
			out.println("				</div> ");
			out.println("			  </div> ");
			out.println("		</form> ");
			out.println(" ");
			out.println("		<!-- Optional JavaScript --> ");
			out.println("		<!-- jQuery first, then Popper.js, then Bootstrap JS --> ");
			out.println("		<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script> ");
			out.println("		<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" crossorigin=\"anonymous\"></script> ");
			out.println("		<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin=\"anonymous\"></script> ");
			out.println("	</body> ");
			out.println("</html> ");
		}catch (Exception e) {
			logger.error("Erro ao montar interface HTML! Motivo: " + e.getMessage());
		}
	}
	
    public static Properties getProp() throws IOException {
        Properties props = new Properties();
        
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream input = loader.getResourceAsStream("jndi.properties");
        if (input == null) {
            throw new IllegalStateException("Could not find jndi.properties in class path");
        }
        props.load(input);
        return props;
    }

    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final Context ctx;
		final Queue queue;

		final ObjectMessage msgObj;

		QueueConnection queueConnection = null;
		QueueSession queueSession = null;
		QueueSender queueSender = null;
		
		try {
			
			Properties prop = getProp();
			
			ctx = new InitialContext();

			queue = (Queue) ctx.lookup(req.getParameter("queue"));

			QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");

			queueConnection = factory.createQueueConnection(prop.getProperty("connectionfactory.Username"), prop.getProperty("connectionfactory.Password"));
			queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			queueSender = queueSession.createSender(queue);
			msgObj = queueSession.createObjectMessage();

			final String msgStringIsoIn = req.getParameter("msgIn");

			final String msgStringIsoOut = req.getParameter("msgOut");

			BeanMensagem bean = new BeanMensagem();

			bean.setOrigem(req.getParameter("rede"));
			bean.setIsoIn(msgStringIsoIn);
			bean.setIsoOut(msgStringIsoOut);

			msgObj.setObject(bean);

			queueSender.send(msgObj);
		} catch (Exception e) {
			logger.error("Erro ao enviar mensagem! Motivo: " + e.getMessage());
		} finally {
			if (queueConnection != null) {
                try {
                	queueConnection.close();
                } catch (JMSException je) {
                	logger.error("Erro ao fechar QueueConnection! Motivo: " + je.getMessage());
                }
            }
			
			if (queueSession != null) {
                try {
                	queueSession.close();
                } catch (JMSException je) {
                	logger.error("Erro ao fechar QueueSession! Motivo: " + je.getMessage());
                }
            }
			
			if (queueSender != null) {
                try {
                	queueSender.close();
                } catch (JMSException je) {
                	logger.error("Erro ao fechar QueueSender! Motivo: " + je.getMessage());
                }
            }
		}
	}

}