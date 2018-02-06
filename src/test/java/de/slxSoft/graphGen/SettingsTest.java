package de.slxSoft.graphGen;

public class SettingsTest {

    public static void main(String[] args) {
        new SettingsTest();
    }

    private SettingsTest() {
        Generator gen1 = new Generator();

//        Graph[] grap1 = gen1.addGraphs(3);
//
//        grap1[1].setSize(5);
//        grap1[1].setFunction("100+x");
//        grap1[1].setRelativeToLast(false);
//        grap1[1].setMaxOffset(0.8);
//        grap1[2].setSize(3);
//        grap1[2].setSubdecimals(3);
//        grap1[2].setChangeProbability(3);
//        grap1[2].setCooldownSpeed(50);

        gen1.addGraph();
        Graph grap1 = gen1.addGraph();
        Graph grap2 = gen1.addGraph();

        grap1.setSize(5);
        grap1.setFunction("100+x");
        grap1.setRelativeToLast(false);
        grap1.setMaxOffset(0.8);
        grap2.setSize(3);
        grap2.setSubdecimals(3);
        grap2.setChangeProbability(3);
        grap2.setCooldownSpeed(50);

        Generator gen2 = new Generator(gen1.getSettings());

        System.out.println(gen1.getSettings());
        System.out.println(gen2.getSettings());
        System.out.println();
        double[][] val1 = gen1.generate();
        double[][] val2 = gen2.generate();

        for (int i = 0; i < val1.length; i++) {
            for (int j = 0; j < val1[i].length; j++)
                System.out.print(val1[i][j] + ", ");
            System.out.println();
        }

        System.out.println();

        for (int i = 0; i < val2.length; i++) {
            for (int j = 0; j < val2[i].length; j++)
                System.out.print(val2[i][j] + ", ");
            System.out.println();
        }
    }
}
