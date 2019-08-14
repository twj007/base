import java.util.ArrayList;
import java.util.List;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/08/07
 **/
public class TestResponseChain {

    public static void main(String[] args) {
        List<Chain> filters = new ArrayList<>();
        Chain c1 = new ResponseChain();
        Chain c2 = new ResponseChain2();
        filters.add(c1); filters.add(c2);
        ChainExecutor executor = new ChainExecutor(filters);
        executor.execute();
    }

}

class ChainExecutor{

    private List<Chain> filters;

    public ChainExecutor(List<Chain> filters){
        this.filters = filters;
    }

    public void execute(){
        if(filters == null || filters.size() == 0){
            return;
        }
        for(Chain filter : filters){
            filter.doFilter();
        }
    }

}

interface Chain{

    void doFilter();
}

class ResponseChain implements Chain{

    @Override
    public void doFilter() {
        System.out.println("chian1");
    }
}

class ResponseChain2 implements Chain{

    @Override
    public void doFilter() {
        System.out.println("chain2");
    }
}
