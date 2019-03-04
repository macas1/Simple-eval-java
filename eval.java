// one class needs to have a main() method
public class eval
{
  public static void main(String[] args)
  {
    System.out.print(eval_main("3 + 4*5"));
  }
  public static String eval_main(String str){
    int       opCount  = eval_countOperations(str);
    char[]    ops      = eval_getOperations(str, opCount);
    float[]   values   = eval_getValues(str, opCount);
    int[]     flags    = {0, opCount+1}; //boolean flag, used elements in values array
    
    while(flags[0]==0){eval_divide(ops, values, flags);}
    flags[0] = 0;
    while(flags[0]==0){eval_multiply(ops, values, flags);}
    flags[0] = 0;
    while(flags[0]==0){eval_add(ops, values, flags);}
    flags[0] = 0;
    while(flags[0]==0){eval_subtract(ops, values, flags);}
    
    return Float.toString(values[0]);
  }
  
  public static int eval_countOperations(String str){
    char[] charArr = str.toCharArray(); 
    int count = 0;
    
    for(int i=0;i<charArr.length;i++){
      if(charArr[i]=='/' || charArr[i]=='*' || charArr[i]=='+' || charArr[i]=='-'){
        count += 1;
      }
    }
    
    return count;
  }
  
  public static char[] eval_getOperations(String str, int opCount){
    char[] charArr = str.toCharArray(); 
    char [] output = new char[opCount];
    int index = 0;
    
    for(int i=0;i<charArr.length;i++){
      if(charArr[i]=='/' || charArr[i]=='*' || charArr[i]=='+' || charArr[i]=='-'){
        output[index] = charArr[i];
        index += 1;
      }
    }
    
    return output;
  }
  
  public static float[] eval_getValues(String str, int opCount){
    char[] charArr = str.toCharArray(); 
    float[] output = new float[opCount+1];
    int index = 0;
    String currentNum = "";
    
    for(int i=0;i<charArr.length;i++){
      if(charArr[i]==' '){continue;}
      if(charArr[i]=='/' || charArr[i]=='*' || charArr[i]=='+' || charArr[i]=='-'){
        output[index] = Float.parseFloat(currentNum);
        currentNum = "";
        index += 1;
      }else{
        currentNum += charArr[i];
      }
    }
    
    output[index] = Float.parseFloat(currentNum);
    
    return output;
  }
  
  public static void eval_shift(float[] values, int index, int used){
    for(int i=index+1;i<used-1;i++){
      values[i] = values[i+1];
    }
  }
  
  public static void eval_shift(char[] ops, int index){
    for(int i=index;i<ops.length-1;i++){
      ops[i] = ops[i+1];
    }
    ops[ops.length-1] = ' ';
  }
  
  public static void eval_divide(char[] ops, float[] values, int[] flags){
    //Get index of /
    int index = -1;
    for(int i=0;i<ops.length;i++){
      
      if(ops[i]=='/'){
        index = i;
        break;
      }
    }
    
    if(index == -1){ //Return if no changed
      flags[0] = 1;
      return;
    }
      
    //Do calculation
    float ans = values[index]/values[index+1];
    
    //Replace values
    values[index] = ans;
    eval_shift(values, index, flags[1]);
    eval_shift(ops, index);
    flags[1] -= 1;
  }
  
  public static void eval_multiply(char[] ops, float[] values, int[] flags){
    //Get index of *
    int index = -1;
    for(int i=0;i<ops.length;i++){
      if(ops[i]=='*'){
        index = i;
        break;
      }
    }
    
    if(index == -1){ //Return if no changed
      flags[0] = 1;
      return;
    }
      
    //Do calculation
    float ans = values[index]*values[index+1];
    
    //Replace values
    values[index] = ans;
    eval_shift(values, index, flags[1]);
    eval_shift(ops, index);
    flags[1] -= 1;
  }
  
  public static void eval_add(char[] ops, float[] values, int[] flags){
    //Get index of +
    int index = -1;
    for(int i=0;i<ops.length;i++){
      if(ops[i]=='+'){
        index = i;
        break;
      }
    }
    
    if(index == -1){ //Return if no changed
      flags[0] = 1;
      return;
    }
      
    //Do calculation
    float ans = values[index]+values[index+1];
    
    //Replace values
    values[index] = ans;
    eval_shift(values, index, flags[1]);
    eval_shift(ops, index);
    flags[1] -= 1;
  }
  
  public static void eval_subtract(char[] ops, float[] values, int[] flags){
    //Get index of -
    int index = -1;
    for(int i=0;i<ops.length;i++){
      if(ops[i]=='-'){
        index = i;
        break;
      }
    }
    
    if(index == -1){ //Return if no changed
      flags[0] = 1;
      return;
    }
      
    //Do calculation
    float ans = values[index]-values[index+1];
    
    //Replace values
    values[index] = ans;
    eval_shift(values, index, flags[1]);
    eval_shift(ops, index);
    flags[1] -= 1;
  }
}
