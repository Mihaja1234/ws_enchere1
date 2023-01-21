package Enchere.connexion;
import java.sql.*;

public class Connexion
{
    Connection con;
    public Statement stat;
    ResultSet res;

    public Connexion(String req)
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            this.con = DriverManager.getConnection("jdbc:postgresql://containers-us-west-96.railway.app:5862/railway", "postgres", "cEfbRTpJZVYdGWEmQgFb");
            this.stat= this.con.createStatement();
//  			this.res=stat.executeQuery(req);
            stat.execute(req);
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public Connection getConnection(){
        return this.con;
    }
    public Connexion()
    {
        try {
            Class.forName("org.postgresql.Driver");
            this.con = DriverManager.getConnection("jdbc:postgresql://containers-us-west-96.railway.app:5862/railway", "postgres", "cEfbRTpJZVYdGWEmQgFb");
        } catch (Exception e) {
        } finally {
        }
    }


    public Connexion(String req,String ide)
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            this.con = DriverManager.getConnection("jdbc:postgresql://containers-us-west-96.railway.app:5862/railway", "postgres", "cEfbRTpJZVYdGWEmQgFb");
            this.stat= this.con.createStatement();
            this.res=stat.executeQuery(req);
            //	stat.execute(req);
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public PreparedStatement prepareStatement(String query) throws SQLException{
        return this.con.prepareStatement(query);

    }
    public ResultSet getResultset()
    {
        return this.res;
    }
    public void getCommit() throws Exception
    {
        this.stat.executeQuery("commit");
    }
    public void getRollBack() throws Exception
    {
        this.stat.executeQuery("rollback");
    }
    public Statement getStat()
    {
        return this.stat;
    }
}

