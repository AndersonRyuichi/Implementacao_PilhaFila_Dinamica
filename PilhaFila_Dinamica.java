public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("______________________________________");
        System.out.println("==============$  Pilha  $=============");
        System.out.println("______________________________________");

        PilhaDinamica p = new PilhaDinamica();

        p.empilha(5);
        p.empilha(4);
        p.empilha(3);

        p.imprimePilha();

        int d1 = p.desempilha();
        int d2 = p.desempilha();

        System.out.println(" Desempilhado 1: " + d1);
        System.out.println(" Desempilhado 2: " + d2);

        p.imprimePilha();

        System.out.println("______________________________________");
        System.out.println("========$  Valida Expressao  $========");
        System.out.println("______________________________________");

        ValidadaExpressaDinamico ev = new ValidadaExpressaDinamico();

        String exp1 = "({1-2) + 3}";
        String exp2 = "[(4+3)+3]";
        String exp3 = "}";

        boolean ev1 = ev.valida_uma_expressao(exp1);
        boolean ev2 = ev.valida_uma_expressao(exp2);
        boolean ev3 = ev.valida_uma_expressao(exp3);

        System.out.println(exp1 + "  -->  " + ev1);
        System.out.println(exp2 + "  -->  " + ev2);
        System.out.println(exp3 + "  -->  " + ev3);


        System.out.println("______________________________________");
        System.out.println("==============$  Fila  $==============");
        System.out.println("______________________________________");

        FilaDinamica FD = new FilaDinamica();

        FD.in(1);
        FD.in(6);
        imprimirRemovido(FD.re());
        FD.in(3);
        FD.in(1);
        FD.in(4);
        FD.in(1);
        FD.imprime();
        System.out.print("\n");

        FD.in(1);
        FD.in(3);
        imprimirRemovido(FD.re());

        FD.in(-65);
        FD.imprime();
        System.out.print("\n");

        FD.re();
        FD.re();
        FD.imprime();
        System.out.print("\n");

        System.out.print("\n");
        System.out.println("______________________________________");
        System.out.println("==============$  Merge  $=============");
        System.out.println("______________________________________");
        var M_D = new MergeDinamico();

        var F_D1 = new FilaDinamica();
        var F_D2 = new FilaDinamica();

        F_D1.in(76);
        F_D1.in(32);
        F_D1.in(12);
        F_D1.in(35);

        F_D2.in(19);
        F_D2.in(56);
        F_D2.in(36);
        F_D2.in(69);
        F_D2.in(48);

        System.out.print("Fila a: ");
        F_D1.imprime();
        System.out.print("\n");
        System.out.print("Fila b: ");
        F_D2.imprime();

        FilaDinamica F_D_Final = M_D.mergeDeFilasOrdenadas(F_D1, F_D2);
        System.out.print("\n");
        System.out.print("Fila c: ");

        F_D_Final.imprime();
    }

    private static void imprimirRemovido(int removido) {
        System.out.println(" Removido: | " + removido + " |");
    }

}

public class PilhaDinamica {
    private ListaEncadeada _primeiro;

    PilhaDinamica() {
        _primeiro = new ListaEncadeada();
    }

    public boolean isVazia() {
        return _primeiro.isVazia();
    }

    public void empilha(int elemento) {
        _primeiro.inUltimo(elemento);
    }

    public int desempilha() throws Exception {
        if(isVazia())
            throw new Exception("Pilha vazia! Nao foi possivel desempilhar.");

        Node removido = _primeiro.reUltimo();

        return removido._info;
    }

    public void imprimePilha() throws Exception {
        _primeiro.imprime();
    }
}

class ValidadaExpressaDinamico {
    private PilhaDinamica _pilha;

    public boolean valida_uma_expressao(String expressao) throws Exception {
        _pilha = new PilhaDinamica();

        char[] charsExpressao = expressao.toCharArray();

        for (char c: charsExpressao) {
            if (c == '(' || c == '['  || c == '{') {
                _pilha.empilha(c);
            }
            else if (c == ')' || c == ']' || c == '}') {
                if(_pilha.isVazia() || !isIgual((char)_pilha.desempilha(), c))
                    return false;
            }
        }

        return _pilha.isVazia();
    }

    private boolean isIgual(char abrir, char fechar) {
        return abrir == '(' && fechar == ')' || abrir == '[' && fechar == ']' || abrir == '{' && fechar == '}';
    }
}

public class FilaDinamica {
    private final ListaEncadeada _primeiro;

    FilaDinamica() {
        _primeiro = new ListaEncadeada();
    }

    public boolean isVazia() {
        return _primeiro.isVazia();
    }

    public void in(int elemento) {
        _primeiro.inUltimo(elemento);
    }

    public int re() throws Exception {
        if(isVazia())
            throw new Exception("Fila vazia! Nao foi possivel remover o elemneto.");

        Node removido = _primeiro.rePrimeiro();

        return removido._info;
    }

    public void imprime() throws Exception {
        _primeiro.imprime();
    }
}

class MergeDinamico {
    public FilaDinamica mergeDeFilasOrdenadas(FilaDinamica fila1, FilaDinamica fila2) throws Exception {
        FilaDinamica filaFinal = new FilaDinamica();

        if (fila1.isVazia()) return completar(filaFinal, fila2);
        if (fila2.isVazia()) return completar(filaFinal, fila1);

        int e1 = fila1.re();
        int e2 = fila2.re();

        while (true) {
            if (e1 <= e2) {
                filaFinal.in(e1);
                if (fila1.isVazia()) {
                    filaFinal.in(e2);
                    return completar(filaFinal, fila2);
                }
                e1 = fila1.re();
            }
            else {
                filaFinal.in(e2);
                if (fila2.isVazia()) {
                    filaFinal.in(e1);
                    return completar(filaFinal, fila1);
                }
                e2 = fila2.re();
            }
        }
    }

    private FilaDinamica completar(FilaDinamica filaFinal, FilaDinamica filaAuxiliar) throws Exception {
        while(true) {
            if (filaAuxiliar.isVazia())
                return filaFinal;
            filaFinal.in(filaAuxiliar.re());
        }
    }
}
