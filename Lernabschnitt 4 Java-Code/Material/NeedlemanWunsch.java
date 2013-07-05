import javax.swing.JOptionPane;
	public class NeedlemanWunsch {
		
        char[] mSeqA;
        char[] mSeqB;
        int[][] bewertungs_matrix;
        int mWertung;
        String mAlignmentSeqA = "";
        String mAlignmentSeqB = "";
        int luecke=-1;
        int gleich = 0;
        int lueckeA = 0;
        int lueckeB =0;
        void bewertungs_matrix_fuellen() {
        	sequenzen_eingeben();
            initialisiere_bewertungs_matrix();
                for (int reihe = 1; reihe <= mSeqA.length; reihe++) {
                        for (int spalte = 1; spalte <= mSeqB.length; spalte++) {
                               zwischenergebnisse_berechnen(spalte,reihe);
                                bewertungs_matrix[reihe][spalte] = maximum(gleich, lueckeA, lueckeB);
                        }
                }
        }
        
        private void zwischenergebnisse_berechnen(int spalte, int reihe) {
        	  gleich = bewertungs_matrix[reihe-1][spalte-1] + vergleiche(reihe, spalte);
              lueckeA = bewertungs_matrix[reihe][spalte-1] +luecke;
              lueckeB = bewertungs_matrix[reihe-1][spalte] +luecke;			
		}

		void backtracking() {
                int reihe = mSeqA.length;
                int spalte = mSeqB.length;
                mWertung = bewertungs_matrix[reihe][spalte];
                while (reihe > 0 && spalte > 0) {  
                		int gleich = bewertungs_matrix[reihe-1][spalte-1] + vergleiche(reihe, spalte);
                		int lueckeA = bewertungs_matrix[reihe][spalte-1] +luecke;
                        int lueckeB = bewertungs_matrix[reihe-1][spalte] +luecke;
                        if (bewertungs_matrix[reihe][spalte] == gleich) {                          
                                mAlignmentSeqA += mSeqA[reihe-1];
                                mAlignmentSeqB += mSeqB[spalte-1];
                                reihe--;
                                spalte--;                            
                                continue;
                        } else if (bewertungs_matrix[reihe][spalte] == lueckeA) {
                                mAlignmentSeqA += "-";
                                mAlignmentSeqB += mSeqB[spalte-1];
                                spalte--;
                                continue;
                        } else if (bewertungs_matrix[reihe][spalte] ==lueckeB){
                                mAlignmentSeqA += mSeqA[reihe-1];
                                mAlignmentSeqB += "-";
                                reihe--;
                                continue;
                        }
                }
                while(reihe>0){
                	mAlignmentSeqA += mSeqA[reihe-1];
                    mAlignmentSeqB += "-";
                    reihe--;
                }
                while(spalte>0){
                    mAlignmentSeqA += "-";
                    mAlignmentSeqB += mSeqB[spalte-1];
                    spalte--;
                	
                }
                mAlignmentSeqA = woerter_umkehren(mAlignmentSeqA);
                mAlignmentSeqB = woerter_umkehren(mAlignmentSeqB);
        }

        void initialisiere_bewertungs_matrix() {
            bewertungs_matrix = new int[mSeqA.length + 1][mSeqB.length + 1];
            for (int reihe = 0; reihe <= mSeqA.length; reihe++) {
                    for (int spalte = 0; spalte <= mSeqB.length; spalte++) {
                            if ((reihe == 0)&&(spalte!=0)) {
                                    bewertungs_matrix[reihe][spalte] = bewertungs_matrix[reihe][spalte-1]+luecke;
                            } else if ((spalte == 0)&&(reihe!=0)) {
                                    bewertungs_matrix[reihe][spalte] = bewertungs_matrix[reihe-1][spalte]+luecke;
                            } else {
                                    bewertungs_matrix[reihe][spalte] = 0;
                            }
                    }
            }
    }
    
        private int maximum(int eins, int zwei, int drei){
        	return Math.max(Math.max(eins, zwei), drei);
        }
       
        /*ausgelagerte Funktionen */ 
        
        private String woerter_umkehren(String alignment){
        	return new StringBuffer(alignment).reverse().toString();
        }
        
        private int vergleiche(int reihe, int spalte) {
        	int equal=3;
        	int notequal=0;
                if (mSeqA[reihe - 1] == mSeqB[spalte - 1]) {
                        return equal;
                } else {
                        return notequal;
                }
        }
       
        void ergebnis_ausgeben(){
            matrix_ausgeben();
            bewertung_und_alignments_ausgeben();
        }
        
        void matrix_ausgeben() {
                System.out.println("bewertungs_matrix =");
                String t=new String(mSeqA);
                t=("_"+t);
                String z=new String(mSeqB);
                z=("_"+z);
                for (int reihe = 0; reihe < mSeqA.length + 1; reihe++) {
                		if(reihe<t.length()){ 
                			System.out.print("    "+t.charAt(reihe));
                			}
                        for (int spalte = 0; spalte < mSeqB.length + 1; spalte++) {
                        	if((spalte<z.length())&&(reihe==0)){
                        		System.out.print("    "+z.charAt(spalte));
                        		if((spalte ==mSeqB.length)&&(reihe==0)){System.out.println();System.out.print("    _");}
                        		}
                        }

                        for (int spalte = 0; spalte < mSeqB.length + 1; spalte++) {	
                            System.out.print(String.format("%4d ", bewertungs_matrix[reihe][spalte]));
                        }
                        System.out.println();
                }
                System.out.println();
        }
        
        void bewertung_und_alignments_ausgeben() {
                System.out.println("Bewertung: " + mWertung);
                System.out.println("Sequenz A: " + mAlignmentSeqA);
                System.out.println("Sequenz B: " + mAlignmentSeqB);
                System.out.println();
        }
        
        void sequenzen_eingeben(){
    		String ant1 = JOptionPane.showInputDialog( null, 
    				"Bitte gib eine Sequenz A ein:" );
    				mSeqA=new char[ant1.length()];
    				ant1=ant1.toUpperCase(); 
    				for(int buchstab=0;buchstab<ant1.length();buchstab++){
    					switch (ant1.charAt(buchstab)){
    					case 'C':
    					case 'T':
    					case 'A':
    					case 'G':	mSeqA[buchstab]=ant1.charAt(buchstab);
    								break;
    					default: 	break;
    					}
    				}
    		String ant2 = JOptionPane.showInputDialog( null, 
    	    	    "Bitte gib eine Sequenz B ein:" );
    	    		mSeqB=new char[ant2.length()];
    	    		ant2=ant2.toUpperCase();
    	    		for(int buchstab=0;buchstab<ant2.length();buchstab++){
    	    			switch (ant2.charAt(buchstab)){
    					case 'C':
    					case 'T':
    					case 'A':
    					case 'G':	mSeqB[buchstab]=ant2.charAt(buchstab);
    								break;
    					default: 	break;
    					}
    	    		}
    	      	}
        
        public static void main(String [] args) {     
                NeedlemanWunsch nw = new NeedlemanWunsch();
                nw.bewertungs_matrix_fuellen();
                nw.backtracking();
                nw.ergebnis_ausgeben();
        }
}
