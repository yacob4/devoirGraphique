package Vues;

import Controlers.CtrlGraphique;
import Entities.DatasGraph;
import Tools.ConnexionBDD;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultKeyedValues2DDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;

public class FrmGraphique extends JFrame{
    private JPanel pnlGraph1;
    private JPanel pnlGraph2;
    private JPanel pnlGraph3;
    private JPanel pnlGraph4;
    private JPanel pnlRoot;
    CtrlGraphique ctrlGraphique;

    public FrmGraphique() throws SQLException, ClassNotFoundException
    {
        this.setTitle("Devoir graphique");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        ConnexionBDD cnx = new ConnexionBDD();
        ctrlGraphique = new CtrlGraphique();


        DefaultCategoryDataset donnees = new DefaultCategoryDataset();



        for (Map.Entry valeur : ctrlGraphique.GetDatasGraphique1().entrySet())
        {

            donnees.setValue(Double.parseDouble(valeur.getValue().toString()), "age", valeur.getKey().toString());
        }
        JFreeChart chart1 = ChartFactory.createLineChart("Salaire Moyen par age ",
                        "Age",
                        "Salaire Moyen",
                donnees,
                PlotOrientation.VERTICAL,
                false,
                false,
                true);
        ChartPanel fra = new ChartPanel(chart1);
        CategoryPlot plot = (CategoryPlot) chart1.getPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
        pnlGraph1.add(fra);
        pnlGraph1.validate();





        DefaultPieDataset dataset = new DefaultPieDataset( );
        int poursentatge;
        String sexe;
        for (String valeur : ctrlGraphique.GetDatasGraphique3().keySet())
        {
            poursentatge = ctrlGraphique.GetDatasGraphique3().get(valeur);
            sexe = valeur;

            dataset.setValue(sexe,poursentatge);
        }
        JFreeChart chart2 = ChartFactory.createRingChart("poursentage par sexe",
                dataset,
                true,
                true,
                true);
        RingPlot plot2 = (RingPlot) chart2.getPlot();
        plot2.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}"));
        //plot.setSectionDepth(0.5);
        ChartPanel fra2 = new ChartPanel(chart2);
        pnlGraph3.add(fra2);
        pnlGraph3.validate();

        fra2.setMouseWheelEnabled(true);

        donnees = new DefaultCategoryDataset();
        Double montant;
        String nomMagazin;
        String secteur;
        for (Map.Entry valeur : ctrlGraphique.GetDatasGraphique4().entrySet())
        {
            montant= Double.parseDouble(((String[])valeur.getValue())[2]);
            nomMagazin= ((String[])valeur.getValue())[1];
            secteur= ((String[])valeur.getValue())[0];
            donnees.setValue(montant,nomMagazin,secteur);
        }

        JFreeChart chart3 = ChartFactory.createBarChart(
                "montant des magazin par semestre",
                "Magazin",
                "montant",
                donnees,
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel fra3 = new ChartPanel(chart3);
        pnlGraph4.add(fra3);
        pnlGraph4.validate();


        DefaultKeyedValues2DDataset donneesGraph2 = new DefaultKeyedValues2DDataset ();
        int nb;
        String trancheAge;
        String sexe2;
        for (Map.Entry valeur : ctrlGraphique.GetDatasGraphique2().entrySet())
        {

            nb= Integer.parseInt(((String[])valeur.getValue())[2]);
            trancheAge= ((String[])valeur.getValue())[0];
            sexe2= ((String[])valeur.getValue())[1];

            if (sexe2.equals("Homme"))
            {
                donneesGraph2.setValue(-nb,sexe2,trancheAge);
            }
            else
            {
                donneesGraph2.setValue(nb,sexe2,trancheAge);
            }

        }
        JFreeChart chart4 = ChartFactory.createStackedBarChart(
                "Pyramide des age",
                "age",
                "Homme/femme",
                donneesGraph2,
                PlotOrientation.HORIZONTAL,
                true, true, false);
        ChartPanel fra4 = new ChartPanel(chart4);
        pnlGraph2.add(fra4);
        pnlGraph2.validate();







    }
}
