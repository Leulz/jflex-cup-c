package codegen;

import model.Operation;

public class Templates {

    public static String operation(Operation op, int label, String dest, String op1, String op2) {
        String opTemplate = operation(op);

        return String.format(opTemplate, label, dest, op1, op2);
    }

    public static String operation(Operation op) {
        String template = null;

        // https://www.doc.ic.ac.uk/lab/secondyear/spim/node16.html

        switch (op) {
        case PLUS: // +
            template = "%d: ADD %s, %s, %s\n";
            break;
        case MINUS: // -
            template = "%d: SUB %s, %s, %s\n";
            break;
        case PLUSEQ: // +=
            template = "%d: ADD %s, %s, %s\n";
            break;
        case MINUSEQ: // -=
            template = "%d: SUB %s, %s, %s\n";
            break;
        case MULT: // *
            template = "%d: MUL %s, %s, %s\n";
            break;
        case MULTEQ: // *=
            template = "%d: MUL %s, %s, %s\n";
            break;
        case DIV: // /
            template = "%d: DIV %s, %s, %s\n";
            break;
        case DIVEQ: // /=
            template = "%d: DIV %s, %s, %s\n";
            break;
        case PLUSPLUS: // ++
            template = "%d: ADD %s, %s, %s\n";
            break;
        case MINUSMINUS: // --
            template = "%d: SUB %s, %s, %s\n";
            break;
        case MOD: // %
            template = "%d: MOD %s, %s, %s\n";
            break;
        case MODEQ: // %=
            template = "%d: MOD %s, %s, %s\n";
            break;
        case EQEQ: // ==
            template = "%d: SUB %s, %s, %s\n";
            break;
        case GTEQ: // >=
            template = "%d: SUB %s, %s, %s\n";
            break;
        case LTEQ: // <=
            template = "%d: SUB %s, %s, %s\n";
            break;
        case NOTEQ: // !=
            template = "%d: NEQ %s, %s, %s\n";
            break;
        case XOREQ:
            template = "%d: XOR %s, %s, %s\n";
            break;
        case LT: // <
            template = "%d: SUB %s, %s, %s\n";
            break;
        case GT: // >
            template = "%d: SUB %s, %s, %s\n";
            break;
        case OR: // |
            template = "%d: OR %s, %s, %s\n";
            break;
        case AND: // &
            template = "%d: AND %s, %s, %s\n";
            break;
        case NOT: // !
            template = "%d: NOT %s, %s, %s\n";
            break;
        case XOR: // ^
            template = "%d: XOR %s, %s, %s\n";
            break;
        case ANDEQ: // &=
            template = "%d: XOR %s, %s, %s\n";
            break;
        case OROR: // ||
            template = "%d: OR %s, %s, %s\n";
            break;
        case OREQ: // |=
            template = "%d: OR %s, %s, %s\n";
            break;
        case ANDAND: // &&
            template = "%d: AND %s, %s, %s\n";
            break;
        }

        return template;
    }

    public static String branchBLTZ(int label, String reg) {
        return String.format("%d: BLTZ %s, %s\n", label, reg, "%d");
    }

    public static String branchBGTZ(int label, String reg) {
        return String.format("%d: BGTZ %s, %s\n", label, reg, "%d");
    }

    public static String branchBLEZ(int label, String reg) {
        return String.format("%d: BLEZ %s, %s\n", label, reg, "%d");
    }

    public static String branchBGEZ(int label, String reg) {
        return String.format("%d: BGEZ %s, %s\n", label, reg, "%d");
    }

    public static String branchBEQZ(int label, String reg) {
        return String.format("%d: BEQZ %s, %s\n", label, reg, "%d");
    }

    public static String branch(int label, String dest) {
        return String.format("%d: BR %s\n", label, dest);
    }

    public static String store(int label, String dest, String value) {
        return String.format("%d: ST %s, %s\n", label, dest, value);
    }

    public static String load(int label, String dest, String value) {
        return String.format("%d: LD %s, %s\n", label, dest, value);
    }

    public static String branchBNEZ(int label, String reg) {
        return String.format("%d: BNEZ %s, %s\n", label, reg, "%d");

    }
}