package Controlers;

import Entities.DatasGraph;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlGraphique
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlGraphique() {
        cnx = ConnexionBDD.getCnx();
    }


    public HashMap<Integer,Double> GetDatasGraphique1()
    {
        HashMap<Integer, Double> datas = new HashMap();
        try {
            cnx = ConnexionBDD.getCnx();
            ps = cnx.prepareStatement("SELECT ageEmp,SUM(salaireEmp)/COUNT(salaireEmp)\n" +
                    "FROM employe\n" +
                    "GROUP BY ageEmp\n");
            rs = ps.executeQuery();
            while(rs.next())
            {
                datas.put(rs.getInt(1), rs.getDouble(2));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datas;
    }

    public HashMap<String,Integer> GetDatasGraphique3()
    {
        HashMap<String, Integer> datas = new HashMap();
        try {
            ps = cnx.prepareStatement("SELECT sexe,COUNT(numEmp)/(SELECT COUNT(numEmp)\n" +
                    "FROM employe)*100 AS pourcent \n" +
                    "FROM employe\n" +
                    "GROUP BY sexe");
            rs = ps.executeQuery();
            while(rs.next())
            {
                datas.put(rs.getString("sexe"), rs.getInt("pourcent"));
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datas;
    }
    public HashMap<Integer,String[]> GetDatasGraphique4()
    {
        HashMap<Integer,String[]> datas = new HashMap();
        try {
            cnx = ConnexionBDD.getCnx();
            ps = cnx.prepareStatement("SELECT nomSemestre,nomMagasin,montant\n" +
                    "    FROM vente\n" +
                    "    GROUP BY nomSemestre,nomMagasin");
            rs = ps.executeQuery();
            int i = 1;
            while(rs.next())
            {
                datas.put(i, new String[]{rs.getString(1),rs.getString(2), String.valueOf(rs.getInt(3))});
                i++;
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datas;
    }

    public HashMap<Integer,String[]> GetDatasGraphique2()
    {
        HashMap<Integer,String[]> datas = new HashMap();
        try {
            cnx = ConnexionBDD.getCnx();
            ps = cnx.prepareStatement("SELECT \"10.19\",sexe,COUNT(numEmp)\n" +
                    "FROM employe\n" +
                    "WHERE ageEmp BETWEEN 10 AND 19\n" +
                    "GROUP BY sexe\n" +
                    "UNION\n" +
                    "SELECT \"20.29\",sexe,COUNT(numEmp)\n" +
                    "FROM employe\n" +
                    "WHERE ageEmp BETWEEN 20 AND 29\n" +
                    "GROUP BY sexe\n" +
                    "UNION\n" +
                    "SELECT \"30.39\",sexe,COUNT(numEmp)\n" +
                    "FROM employe\n" +
                    "WHERE ageEmp BETWEEN 30 AND 39\n" +
                    "GROUP BY sexe\n" +
                    "UNION\n" +
                    "SELECT \"40.49\",sexe,COUNT(numEmp)\n" +
                    "FROM employe\n" +
                    "WHERE ageEmp BETWEEN 40 AND 49\n" +
                    "GROUP BY sexe\n" +
                    "UNION\n" +
                    "SELECT \">50\",sexe,COUNT(numEmp)\n" +
                    "FROM employe\n" +
                    "WHERE ageEmp>50\n" +
                    "GROUP BY sexe");
            rs = ps.executeQuery();
            int i = 1;
            while(rs.next())
            {
                datas.put(i, new String[]{rs.getString(1),rs.getString(2), String.valueOf(rs.getInt(3))});
                i++;
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datas;
    }

}
