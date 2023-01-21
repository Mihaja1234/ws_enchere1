package Enchere.connexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connexion1 {

        public Connection con;
        public String req;
        public PreparedStatement prepstat;
        public ResultSet res;

        public PreparedStatement getPreparedStatement(String s) throws SQLException {
            try {
                prepstat=getConnection().prepareStatement(s);
                return prepstat;
            } catch (SQLException ex) {
                throw ex;
            }
        }
        public Connexion1() throws SQLException, ClassNotFoundException {
            try {
                Class.forName("org.postgresql.Driver");
                this.con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/rencontre","postgres","hasinjaka");
            } catch (ClassNotFoundException | SQLException ex){
                throw ex;
            }
        }
        public void commit() throws SQLException
        {
            try {
                this.con.commit();
            } catch (SQLException ex) {
                throw ex;
            }
        }

        public Connection getConnection(){
            return this.con;
        }

        public void close() throws SQLException{
            try {
                if(prepstat!=null){
                    prepstat.close();
                }
                if(res!=null){
                    res.close();
                }
                if(con!=null){
                    con.close();
                }
            }catch (SQLException ex) {
                throw ex;
            }
        }

        public ResultSet getResultSet() throws SQLException {
            try {
                res=this.prepstat.executeQuery();
            } catch (SQLException ex) {
                throw ex;
            }
            return res;
        }

        public void setRes(ResultSet res) {
            this.res = res;
        }

    }




