import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.*;
//Restaurant Manager
public class Manager {

    String spatiuPunctat="_________________________________";
    private JButton comandaButton1;
    private JButton achitaButton1;
    private JButton comandaButton2;
    private JButton achitaButton2;
    private JButton comandaButton3;
    private JButton achitaButton3;
    private JButton comandaButton4;
    private JButton achitaButton4;
    private JPanel panouPrincipal;
    private JButton vizualizareButon1;
    private JButton vizualizareButon2;
    private JButton vizualizareButon3;
    private JButton vizualizareButon4;
    private JLabel labelMasa1;
    private JLabel labelMasa2;
    private JLabel labelMasa3;
    private JLabel labelMasa4;
    private JButton anulareButton1;
    private JButton anulareButton2;
    private JButton anulareButton3;
    private JButton anulareButton;
    private JButton incarcaMeniuButton;
    private JList listaMeniu;
    private JCheckBox păstreazaSelecțiaCheckBox;
    private JList listaVizualizareComanda;
    private JLabel labelVizualizareComandaMasa;
    private JButton incasariAstaziButton;
    private JButton raportIncasareButton;

    private JTextPane textPane1;
    ArrayList<String> comandaMasa1=new ArrayList<String>();
    ArrayList<String> comandaMasa2=new ArrayList<String>();
    ArrayList<String> comandaMasa3=new ArrayList<String>();
    ArrayList<String> comandaMasa4=new ArrayList<String>();


    public void salveazaDateleDeLaMasa(ArrayList<String> comandaMasax,int nrMasa)
    {
        try{
            int fisier=nrMasa;
            FileOutputStream fos= new FileOutputStream("masa"+fisier+".dat");
            DataOutputStream dos = new DataOutputStream(fos);
            for (String itema:comandaMasax){
                dos.writeUTF(itema);
            }
            dos.flush();
            fos.close();


        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null,"Eroare la lucrul cu fisiere"+e.toString());
        }
    }
    public void arataSumaIncasataAzi()
    {
        int sumaInitiala=0,sumaFinala=0;
        try{
            String fisier=java.time.LocalDate.now().toString();
            FileInputStream fos= new FileInputStream("incasari_"+fisier+".dat");
            DataInputStream dos = new DataInputStream(fos);
            sumaInitiala=dos.readInt();
            fos.close();
            JOptionPane.showMessageDialog(null,"Suma incasata azi: "+sumaInitiala);
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null,"Nu s-au facut incasari astazi!");
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,"Eroare la lucrul cu fisiere"+e.toString());
        }
    }
    public void updateIncasareDeAzi(ArrayList<String> comandaMasax)
    { //citeste din fisier
        int sumaInitiala=0,sumaFinala=0;
        try
        {
            String fisier = java.time.LocalDate.now().toString();
            String numeFisier = "incasari_" + fisier + ".dat";
            FileInputStream fos = new FileInputStream(numeFisier);
            DataInputStream dos = new DataInputStream(fos);
            sumaInitiala = dos.readInt();
            fos.close();
            //JOptionPane.showMessageDialog(null,"Suma initiala: "+sumaInitiala);
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null,"Nu s-au mai facut alte incasari astazi");
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,"Eroare la lucrul cu fisiere"+e.toString());
        }

        sumaFinala=faTotalul(comandaMasax)+sumaInitiala;
        //scrie in fisier
        try{
            String fisier=java.time.LocalDate.now().toString();
            FileOutputStream fos= new FileOutputStream("incasari_"+fisier+".dat");
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeInt(sumaFinala);
            //JOptionPane.showMessageDialog(null,"Suma finala: "+sumaFinala);
            dos.flush();
            fos.close();


        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null,"Nu s-au mai facut alte incasari astazi sau eroare la lucrul cu fisiere"+e.toString());
        }
    }

    public ArrayList<String> incarcaDateleDeLaMasa(int nrMasa)
    {
        String elemente;
        ArrayList<String> comandaMasa=new ArrayList<>();
        try{
            int fisier=nrMasa;
            FileInputStream ios= new FileInputStream("masa"+fisier+".dat");
            DataInputStream is = new DataInputStream(ios);
            while (is.available()>0)
            {
                elemente=is.readUTF();
                comandaMasa.add(elemente);

            }


            ios.close();
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null,"Eroare la lucrul cu fisiere"+e.toString());
        }

        return comandaMasa;
    }

    public int aflaPret(String produs)
    {
        int indexPret=0;
        for (int k=0;k<preturi.produs.length;k++)
        {
            if (produs.equals(preturi.produs[k]))
            {
                indexPret=k;

            }
        }

        return preturi.pret[indexPret];
    }

    public int faTotalul(ArrayList<String> comandaMasax)
    {
        int total=0;

        for (String itemx:comandaMasax)
        {
          total=total+aflaPret(itemx);
        }

        return total;
    }
    public void seteazaMasaLibera(JLabel masanr,int nrMasa)
    {
        masanr.setText("Masa "+nrMasa+"-liberă");
        masanr.setForeground(Color.blue);
    }
    public void seteazaMasaOcupata(JLabel masanr,int nrMasa)
    {
        masanr.setText("Masa "+nrMasa+"-ocupată");
        masanr.setForeground(Color.red);
    }
    public void adaugareComanda(ArrayList<String>comandaMasax,JLabel labelMasax,int nrMasa,JButton comandaButonx)
    {
        if (listaMeniu.isSelectionEmpty()==true)
        {
            JOptionPane.showMessageDialog(null,"Alegeti produsul din lista !");
        }
        else {
            comandaMasax.add(listaMeniu.getSelectedValue().toString());

            seteazaMasaOcupata(labelMasax, nrMasa);

            salveazaDateleDeLaMasa(comandaMasax, nrMasa);
             }
        if (păstreazaSelecțiaCheckBox.isSelected()==false) listaMeniu.clearSelection();
    }
    public void vizualizareComandaMasa(ArrayList<String> comandaMasax,int nrMasa) {
        int ocurenta = 0;
        String totalComandaMasa = "";
        Set<String> comandaMasaxUnice=new LinkedHashSet<>(comandaMasax);
        DefaultListModel<String> l2 = new DefaultListModel<>();
        for (String itemb : comandaMasaxUnice)
        {
            ocurenta = Collections.frequency(comandaMasax, itemb);
            if (ocurenta > 0) {
                l2.addElement(ocurenta+" x "+ itemb +" : "+ocurenta+" X "+ aflaPret(itemb)+ " = "+aflaPret(itemb)*ocurenta+" RON \n");
                totalComandaMasa = totalComandaMasa +ocurenta+" x "+ itemb +" : "+ocurenta+" X "+ aflaPret(itemb)+ " = "+aflaPret(itemb)*ocurenta+" RON \n";
            }
        }

        listaVizualizareComanda.setModel(l2);
        labelVizualizareComandaMasa.setText("Vizualizare comanda masa "+nrMasa);



    }

    public void achitaComanda(ArrayList<String>comandaMasax,JLabel labelMasax,int nrMasa)
    {

        int ocurenta = 0;
        String totalComandaMasa = "";
        Set<String> comandaMasaxUnice=new LinkedHashSet<String>(comandaMasax);

        for (String itemc : comandaMasaxUnice)
        {
            ocurenta = Collections.frequency(comandaMasax, itemc);
            if (ocurenta > 0) {
                totalComandaMasa = totalComandaMasa +ocurenta+" x "+ itemc +" : "+ocurenta+" X "+ aflaPret(itemc)+ " = "+aflaPret(itemc)*ocurenta+" RON \n";

            }
        }

        String suma=JOptionPane.showInputDialog(null,"BON masa "+nrMasa+" : \n"+totalComandaMasa + spatiuPunctat+" \n Total :" + faTotalul(comandaMasax) + " RON.\n\nIntroduceti suma primită");
        int deReturnatClient=Integer.parseInt(suma)-faTotalul(comandaMasax);

        if(deReturnatClient>=0){
            JOptionPane.showMessageDialog(null,"Rest catre client : "+deReturnatClient+" RON\n"+spatiuPunctat+" \nComanda de la masa "+nrMasa+" a fost achitată!");
            updateIncasareDeAzi(comandaMasax);
            comandaMasax.clear();
            seteazaMasaLibera(labelMasax,nrMasa);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Suma insuficienta. Trebuie achitata suma de "+faTotalul(comandaMasax));
        }

        salveazaDateleDeLaMasa(comandaMasax,nrMasa);
        vizualizareComandaMasa(comandaMasax,nrMasa);
        labelVizualizareComandaMasa.setText("Vizualizare comanda masa");
    }

public void anulareComanda(ArrayList<String> comandaMasax,JLabel labelMasax,int nrMasa)
{
    comandaMasax.clear();
    JOptionPane.showMessageDialog(null,"Comanda de la masa "+nrMasa+" a fost anulată!");
    seteazaMasaLibera(labelMasax,nrMasa);
    salveazaDateleDeLaMasa(comandaMasax,nrMasa);
    vizualizareComandaMasa(comandaMasax,nrMasa);
}
    public Manager() {



        comandaButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adaugareComanda(comandaMasa1,labelMasa1,1,comandaButton1);
                vizualizareComandaMasa(comandaMasa1,1);


            }
        });
        comandaButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adaugareComanda(comandaMasa2,labelMasa2,2,comandaButton2);
                vizualizareComandaMasa(comandaMasa2,2);

            }
        });
        comandaButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adaugareComanda(comandaMasa3,labelMasa3,3,comandaButton3);
                vizualizareComandaMasa(comandaMasa3,3);

            }
        });
        comandaButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adaugareComanda(comandaMasa4,labelMasa4,4,comandaButton4);
                vizualizareComandaMasa(comandaMasa4,4);

            }
        });
        vizualizareButon1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vizualizareComandaMasa(comandaMasa1,1);

            }
        });
        vizualizareButon2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vizualizareComandaMasa(comandaMasa2,2);

            }
        });
        vizualizareButon3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vizualizareComandaMasa(comandaMasa3,3);
            }
        });
        vizualizareButon4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vizualizareComandaMasa(comandaMasa4,4);

            }
        });
        achitaButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                achitaComanda(comandaMasa1,labelMasa1,1);

            }
        });
        achitaButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                achitaComanda(comandaMasa2,labelMasa2,2);
            }
        });
        achitaButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            achitaComanda(comandaMasa3,labelMasa3,3);
            }
        });
        achitaButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            achitaComanda(comandaMasa4,labelMasa4,4);
            }
        });
        anulareButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anulareComanda(comandaMasa1,labelMasa1,1);


            }
        });
        anulareButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anulareComanda(comandaMasa2,labelMasa2,2);

            }
        });
        anulareButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anulareComanda(comandaMasa3,labelMasa3,3);

            }
        });
        anulareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anulareComanda(comandaMasa4,labelMasa4,4);

            }
        });



        incarcaMeniuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    DefaultListModel<String> ld = new DefaultListModel<>();

                    for (int i=0;i<preturi.produs.length;i++)
                    {

                        ld.addElement(preturi.produs[i]);

                    }
                    listaMeniu.setModel(ld);


                    comandaMasa1=incarcaDateleDeLaMasa(1);
                    if (comandaMasa1.isEmpty()) seteazaMasaLibera(labelMasa1,1); else seteazaMasaOcupata(labelMasa1,1);

                    comandaMasa2=incarcaDateleDeLaMasa(2);
                    if (comandaMasa2.isEmpty()) seteazaMasaLibera(labelMasa2,2); else seteazaMasaOcupata(labelMasa2,2);;
                    comandaMasa3=incarcaDateleDeLaMasa(3);
                    if (comandaMasa3.isEmpty()) seteazaMasaLibera(labelMasa3,3); else seteazaMasaOcupata(labelMasa3,3);;
                    comandaMasa4=incarcaDateleDeLaMasa(4);
                    if (comandaMasa4.isEmpty()) seteazaMasaLibera(labelMasa4,4); else seteazaMasaOcupata(labelMasa4,4);;


            }
        });


        incasariAstaziButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            arataSumaIncasataAzi();
            }
        });
    }


    public static void main(String[] args) {
        JFrame rManager=new JFrame("Manager");
        rManager.setContentPane(new Manager().panouPrincipal);
        rManager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rManager.setLocationRelativeTo(null);
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e)
        {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        rManager.pack();
        rManager.setExtendedState(JFrame.MAXIMIZED_BOTH);
        rManager.setVisible(true);



    }
}
