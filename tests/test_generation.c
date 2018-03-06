int const TRUE = 1, FALSE = 0;

int calc(int x, int y, char op) {
	switch(op) {
		case '|':
			return x || y;
		case '&':
			return x && y;
		case '^': case 'x':
			return (x || y) && (!x || !y);
		case '+':
			return x + y;
		case '-':
			x -= y;
			return x;
		case '*': case '.':
			return x*y;
		case '/':
			return x /= y;
		case 'N':
			break;
		default:
			return 0;
	}
	return 1;
}

int turnFloatToInt(float x) {
	return (int) x;
}

int turnStringToInt(char *s) {
	return 1;
}

int main(void) {
	calc(TRUE, FALSE, '|');
	calc(TRUE, FALSE, '&');
	calc(TRUE, turnStringToInt("oi"), '^');
	calc(TRUE, FALSE, 'x');
	calc(TRUE, FALSE, '>');
	calc(0, 021, 'N');
	calc(2, 3, '+');
	calc(3, 444, '-');
	calc(turnFloatToInt(444451.0), 0x545, '/');
	calc(5, 23, '*');
	
	return 0;
}