typedef int i;

void function(int param1, int param2) {
  int ans = param1 + param2;
  for(int i = 0; i<10) { //error here
    ans += i;
  }
  return ans;
}

int main(int abc) {
  int x = function(50,20);
  int a = 5;
}