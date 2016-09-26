int getI(int p);
void swap(int* a,int* b);
int getI(int p){
	int temp = p + 10;
	return temp;
}
void swap(int* a,int* b){
	int temp = *a;
	*a = *b;
	*b = temp;
}
int main(int argc, char* argv) {
	//puti(int val), putc(char c) 是编译器内置函数
	//字符型
	char c = 'c';
	putc(c);
	putc('\n');
	
	//整型
	int i;
	puti(getI(i));
	putc('\n');
	
	//指针型
	int a = 5;
	int b = 6;
	int* pa = &a;
	swap(pa,&b);
	
	puti(*a);
	putc('\n');
	
	puti(*b);
	putc('\n');
	
	//流程控制
	if(2 < get()){
		putc('o');
		putc('k');
		putc('\n');
	}
	
	int sum = 0;
	int count = 0;
	while(count < 10){
		sum = sum + count;
	}
	puti(sum);
	putc('\n');
	
	return 0;
}
