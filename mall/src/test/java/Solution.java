///***
// **@project: base
// **@description:
// **@Author: twj
// **@Date: 2019/07/22
// **/
//public class Solution {
//    public int[] twoSum(int[] nums, int target) {
//        int[] result;
//        int k = 0;
//        for(int i = 0; i < nums.length - 1; i++){
//            for(int j = 1; j < nums.length; j++){
//                if((nums[i] + nums[j]) == target){
//                    result[k] = new int{i};
//                    result[k+1] = nums[j];
//                    k = k+2;
//                }
//            }
//        }
//        return result;
//    }
//
//    public static void main(String []args){
//        Solution s = new Solution();
//        int[] result = s.twoSum(new int[]{2, 7, 11, 15}, 9);
//        for(int e : result){
//            System.out.println(e);
//        }
//    }
//}