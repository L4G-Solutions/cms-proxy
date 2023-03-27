import com.andromeda.commons.util.JsonUtils;

public class Test {

	public static void main(String[] args) {
		int[] strArr = {1,2,3,4};
		String json = JsonUtils.toString(strArr);
		System.out.println(json);

	}

}
