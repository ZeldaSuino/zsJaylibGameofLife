public class Conway {

    private final byte[][] celula;
    private final int lado;

    public Conway(int lado){
        this.lado = lado;
        celula = new byte[lado][lado];

    }

    public void cnwTestShip(){

        celula[2][1] = 1;
        celula[3][2] = 1;
        celula[1][3] = 1; celula[2][3] = 1; celula[3][3] = 1;

        //celula[4][4] = 1;

    }

    public boolean cnwGetCell(int x,int y){
        if(y < lado)
            if (x < lado)
                return celula[x][y] > 0;
        return false;
    }

    public void cnwUpdate(){
        byte[][] temp = new byte[lado][lado];

        for(int y = 0; y < lado; y++) {
            for (int x = 0; x < lado; x++) {
                // REGRAS
                /*
                *  Se a célula tem menos de 2 vizinhos, ela morre.
                *  Se a célula tem 2 vizinhos, ela permanece como está.
                *  Se a célula tem 3 vizinhos, ela recebe vida.
                *  Se a célula tem mais de 3 vizinhos, ela morre.
                *
                * */

                switch(cnwCellSides(x, y)){
                    case 2:  temp[x][y] = celula[x][y]; break;
                    case 3:  temp[x][y] = 1; break;
                    default: temp[x][y] = 0; break;
                }

            }
        }
        System.arraycopy(temp, 0, celula, 0, lado);
    }

    public int cnwCellSides(int x, int y){
        int total = 0;

        int esquerda = x-1;
        int direita = x+1;
        int cima = y-1;
        int baixo = y+1;


        // 3 células da esquerda
        if (esquerda >= 0){
            total += celula[esquerda][y];
            if (cima >= 0){
                total+= celula[esquerda][cima];
            }
            if (baixo < lado) {
                total += celula[esquerda][baixo];
            }
        }
        // 3 células da direita
        if (direita < lado){
            total += celula[direita][y];
            if (cima >= 0){
                total+= celula[direita][cima];
            }
            if (baixo < lado) {
                total += celula[direita][baixo];
            }
        }
        // célula de cima
        if (cima >= 0){
            total += celula[x][cima];
        }
        // célula de baixo
        if (baixo < lado){
            total += celula[x][baixo];
        }

        return total;
        // Valeuzão pro Ben Avrahami.
    }

}
