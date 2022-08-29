import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ProteinTranslator {

    List<String> translate(String rnaSequence) {
		ArrayList<String> list = new ArrayList<>();
		for(int i = 0; i < rnaSequence.length(); i += 3) 
			list.add(rnaSequence.substring(i, i+3)); 
		return codeProteinMapping(list);
    }
    	private List<String> codeProteinMapping(ArrayList<String> list){
		Map<List<String>, String> codeProtein = Stream.of(new Object[][]    {
			{Arrays.asList("AUG"),"Methionine"},
			{Arrays.asList("UUU","UUC"),"Phenylalanine"},
			{Arrays.asList("UUA","UUG"),"Leucine"},
			{Arrays.asList("UCU","UCC","UCA","UCA","UCG"),"Serine"},
			{Arrays.asList("UAU","UAC"),"Tyrosine"},
			{Arrays.asList("UGU","UGC"),"Cysteine"},
			{Arrays.asList("UGG"),"Tryptophan"},
			{Arrays.asList("UAA","UAG","UGA"),"STOP"}
		}).collect(Collectors.toMap(data ->((List<String>) data[0]),                                        data -> (String) data[1]));
		 List<String> rnaList = list.stream()
				 					.takeWhile(e->!e.equals("UAA"))
                                    .takeWhile(e->!e.equals("UAG"))
                                    .takeWhile(e->!e.equals("UGA"))
				 					.map(e->        
                                     codeProtein.entrySet().stream()
									.filter(a->a.getKey().contains(e))
									.findFirst()
									.get()
									.getValue())
									.collect(Collectors.toList());
		return rnaList;
	}
}
