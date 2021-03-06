// Descomentar si se se va a usar un Listener para iniciar la conexi�n:
//package listener;
//Comentar si se va a usar un Listner para iniciar la conexi�n:
package listener;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.logging.Logger;


// Descomentar si se se va a usar un Listener para iniciar la conexi�n:
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;


//Descomentar si se se va a usar un Listener para iniciar la conexi�n:
@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {

//Comentar si se va a usar un Listener para iniciar la conexi�n:
//public class DBConnection {
	
	private static final Logger logger = Logger.getLogger(ServletContextListener.class.getName());
	
	//Descomentar si se se va a usar un Listener para iniciar la conexi�n:
	public void contextInitialized(ServletContextEvent event) {

	//Comentar si se va a usar un Listner para iniciar la conexi�n:
	//public Connection create(){
		
		logger.info("Creating DB");
		Connection conn = null;
		
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			// Descomentar las siguientes l�neas para su uso en nuestra aplicaci�n web y comentar para pruebas solo del DAO:
			ServletContext sc = event.getServletContext();
			
			conn = DriverManager.getConnection("jdbc:hsqldb:file:" + sc.getRealPath("WEB-INF/news/"),
													"sa", // username
													""); //password
			//Comentar las siguientes l�nea para  su uso en nuestra aplicaci�n web y comentar para pruebas solo del DAO:
			//conn = DriverManager.getConnection("jdbc:hsqldb:mem:/localhost/news",
			//		"sa",                     // username
			//		"");					  //password

			Statement stmt = conn.createStatement();
			
			//Init db schema
			try{
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS User (ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 0) NOT NULL PRIMARY KEY,"
						+ "NAME VARCHAR(40) NOT NULL, "
						+ "PASSWORD VARCHAR(40) NOT NULL, "
						+ "EMAIL VARCHAR(50) NOT NULL)" );
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS News (ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 0) NOT NULL PRIMARY KEY, "
						+ "OWNER INTEGER NOT NULL, "
						+ "DATESTAMP DATE default now,"
						+ "TIMESTAMP TIME default '00:00:00',"
						+ "TITLE VARCHAR(150) NOT NULL, "
						+ "TEXT  VARCHAR(400) NOT NULL, "
						+ "URL  VARCHAR(150) NOT NULL, "
						+ "CATEGORY VARCHAR(20) NOT NULL, "
						+ "LIKES INTEGER default 0, "
						+ "HITS INTEGER default 0, "
						+ "IMAGE VARCHAR(150) NOT NULL, "
						+ "FOREIGN KEY (OWNER) REFERENCES USER(ID) ON DELETE CASCADE)" );
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Comment (ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 0) NOT NULL PRIMARY KEY, "
						+ "OWNER INTEGER NOT NULL, "
						+ "NEWS INTEGER NOT NULL, "
						+ "DATESTAMP DATE default now,"
						+ "TIMESTAMP TIME default '00:00:00',"
						+ "TEXT  VARCHAR(400) NOT NULL, "
						+ "LIKES INTEGER default 0, "
						+ "FOREIGN KEY (OWNER) REFERENCES USER(ID) ON DELETE CASCADE," 
						+ "FOREIGN KEY (NEWS) REFERENCES NEWS(ID) ON DELETE CASCADE)" );
				
				
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			//init data
			stmt.executeUpdate("INSERT INTO User (name, password, email) SELECT 'vader', 'anakin', 'darth.vader@darksideoftheforce.org' FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from User where name='vader')  LIMIT 1");
			stmt.executeUpdate("INSERT INTO User (name, password, email) SELECT 'doe', 'john', 'jdoe@nothing.es' FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from User where name='doe')  LIMIT 1");
			
			
			
			stmt.executeUpdate("INSERT INTO News (owner,title,text,url,image,category,likes,hits) "
					+ "SELECT 0,'Un desahucio por cada 850 familias',"
					+"'Extremadura es la segunda comunidad con mejor tasa de viviendas desalojadas por orden judicial En la comunidad autónoma hay más casos de familias que se quedan sin su casa por no abonar el alquiler que por dejar de pagar las letras de la hipoteca',"
					+ "'http://www.hoy.es/extremadura/201602/13/desahucio-cada-familias-20160213194256.html',"
					+ "'http://www.hoy.es/noticias/201602/13/media/94698825._xoptimizadax.jpg','General', '9','60'"
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from News where id=0)  LIMIT 1");
			
			stmt.executeUpdate("INSERT INTO News (owner,title,text,url,image,category,likes,hits) "
					+ "SELECT 1,'Nadal se clasifica para semifinales en Río por la lesión de Dolgopolov',"
					+"'El español Rafael Nadal se clasificó a las semifinales del Abierto de Río de Janeiro por una lesión de su rival, el ucraniano Alexandr Dolgopolov, antes de que se disputase el partido de cuartos de final. Dolgopolov se lesionó el hombro derecho y se vio forzado a renunciar a jugar contra Nadal, según informó la organización de este torneo ATP 500',"
					+ "'http://www.marca.com/tenis/2016/02/20/56c7ab5e46163f6d488b4584.html',"
					+ "'http://e03-marca.uecdn.es/assets/multimedia/imagenes/2016/02/20/14559260915229.jpg','Deportes','10','9'"
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from News where id=1)  LIMIT 1");
			
			stmt.executeUpdate("INSERT INTO News (owner,title,text,url,image,category,likes,hits) "
					+ "SELECT 1,'El 30% del uso de datos se lo llevan las aplicaciones en segundo plano',"
					+"'Millones de usuarios de la aplicación de gestión de datos Opera Max han detectado que más del 30% de sus datos móviles es utilizado por aplicaciones como Whatsapp, Facebook Messenger, Google Drive o Gmail mientras se ejecutan en segundo plano, según un informe de la empresa creadora del navegador Opera',"
					+ "'http://www.elmundo.es/tecnologia/2016/02/19/56c7381546163f94778b457d.html',"
					+ "'http://e03-elmundo.uecdn.es/assets/multimedia/imagenes/2016/02/19/14558965881471.jpg','Ciencia','11','120'"
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from News where id=2)  LIMIT 1");
			
			stmt.executeUpdate("INSERT INTO News (owner,title,text,url,image,category,likes,hits) "
					+ "SELECT 1,'Griezmann está a una amarilla de perderse el derbi',"
					+"'El delantero del Atlético de Madrid podría quedar suspendido para el duelo ante el Real Madrid en el Santiago Bernabéu si recibe una amonestación este domingo frente al Villarreal',"
					+ "'http://www.mundodeportivo.com/futbol/atletico-madrid/20160220/302307556503/griezmann-esta-a-una-amarilla-de-perderse-el-derbi.html',"
					+ "'http://www.mundodeportivo.com/img/941fb6a4-d7eb-11e5-b58b-b631fb087836/lowres/fatbol.jpg','Deportes','12','230'"
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from News where id=3)  LIMIT 1");
			
			stmt.executeUpdate("INSERT INTO News (owner,title,text,url,image,category,likes,hits) "
					+ "SELECT 1,'Air Nostrum despega de Badajoz con un 59% de ocupación el primer día de vuelos',"
					+"'La reanudación de la línea aérea de Badajoz con Madrid y Barcelona esta mañana ha traído en el primer día de operaciones una ocupación media del 59% según la compañía Air Nostrum, que ha rotulado para la ocasión el aparato con un ‘Volvemos a Extremadura’.',"
					+ "'http://www.eldiario.es/eldiarioex/Abrochese-Air-Nostrum-Badajoz-ocupacion_0_485751734.html',"
					+ "'http://images.eldiario.es/eldiarioex/Llegada-Barcelona-Air-Nostrum-Badajoz_EDIIMA20160218_0246_4.jpg','General','13','40'"
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from News where id=4)  LIMIT 1");
						
			stmt.executeUpdate("INSERT INTO Comment (owner,news,text) "
					+ "SELECT 1,0,'Comentario 1' "
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from Comment where id=0)  LIMIT 1");
			stmt.executeUpdate("INSERT INTO Comment (owner,news,text) "
					+ "SELECT 0,0,'Comentario 2' "
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from Comment where id=1)  LIMIT 1");
			
			stmt.executeUpdate("INSERT INTO Comment (owner,news,text) "
					+ "SELECT 0,1,'Comentario 1' "
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from Comment where id=2)  LIMIT 1");
			stmt.executeUpdate("INSERT INTO Comment (owner,news,text) "
					+ "SELECT 1,1,'Comentario 2' "
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from Comment where id=3)  LIMIT 1");
			stmt.executeUpdate("INSERT INTO Comment (owner,news,text) "
					+ "SELECT 0,1,'Comentario 3' "
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from Comment where id=4)  LIMIT 1");
			stmt.executeUpdate("INSERT INTO Comment (owner,news,text) "
					+ "SELECT 0,1,'Comentario 4' "
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from Comment where id=5)  LIMIT 1");
			
			stmt.executeUpdate("INSERT INTO Comment (owner,news,text) "
					+ "SELECT 1,2,'Comentario 1' "
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from Comment where id=6)  LIMIT 1");
			stmt.executeUpdate("INSERT INTO Comment (owner,news,text) "
					+ "SELECT 0,2,'Comentario 2' "
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from Comment where id=7)  LIMIT 1");
			stmt.executeUpdate("INSERT INTO Comment (owner,news,text) "
					+ "SELECT 1,2,'Comentario 3' "
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from Comment where id=8)  LIMIT 1");
			
			// Descomentar las siguientes l�neas para su uso en nuestra aplicaci�n web y comentar para pruebas solo del DAO:
			sc.setAttribute("dbConn", conn);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                     
		
		logger.info("DB created");

		//return conn;
	}
	
	//Descomentar si se se va a usar un Listener para destruir la conexi�n:
    public void contextDestroyed(ServletContextEvent arg0)  { 
	//Comentar si se va a usar un Listener para destruir la conexi�n:	 
	//public void destroy(Connection conn){
		
		logger.info("Destroying DB");
		try {
			logger.info("DB shutdown start");
			//Descomentar si se se va a usar un Listener para destruir la conexi�n:
	   		ServletContext sc = arg0.getServletContext();
	   		Connection conn = (Connection) sc.getAttribute("dbConn");
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("SHUTDOWN");
			conn.close();
			Enumeration<Driver> drivers = DriverManager.getDrivers();
			while (drivers.hasMoreElements()) {
				logger.info("DB deregistering drivers");
				Driver driver = drivers.nextElement();
				try {
					DriverManager.deregisterDriver(driver);
					logger.info(String.format("deregistering jdbc driver: %s", driver));
				} catch (SQLException e) {
					logger.severe(String.format("Error deregistering driver %s %s", driver, e));
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("DB destroyed");
	}

	
   
	
	

}
