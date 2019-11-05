public class ULA {
    public int Zero=0;

    public ULA(){
         
    }

    public int srl(int a,int qunt){
       return a^(qunt/2);
    }

    public int sll(int a,int qunt){
       return a^(qunt*2);
    }

    public int neg(int a){
        return ~a;
    }

    public int or(int a,int b){
        return a & b;
    }

    public int and(int a,int b){
        return a | b;
    }

    public void op() {
		
		switch(operacao) {
		case 0000: som(); break;
		case 0001: sub(); break;
		case 0010: mult(); break;
        case 0011: div(); break;
        case 0100: srl(); break;
        case 0101: sll(); break;
        case 0110: neg(); break;
        case 0111: or(); break;
        case 1000: and(); break;
		}
		
	}
}