
public class Driver {
	public static void main(String[] args) throws Exception {
		
		DataDividerByUser dataDividerByUser = new DataDividerByUser();
		CoOccurrenceMatrixGenerator coOccurrenceMatrixGenerator = new CoOccurrenceMatrixGenerator();
		Normalize normalize = new Normalize();
		Multiplication multiplication = new Multiplication();
		Sum sum = new Sum();
		AverageRating averageRating = new AverageRating();

		String rawInput = args[0];
		String userMovieListOutputDir = args[1];
		String coOccurrenceMatrixDir = args[2];
		String normalizeDir = args[3];
		String multiplicationDir = args[4];
		String sumDir = args[5];
		String averageRatingDir = args[6];
		String movieIDStart = args[7];
		String movieIDSize = args[8];

		String[] path1 = {rawInput, userMovieListOutputDir};
		String[] path2 = {userMovieListOutputDir, coOccurrenceMatrixDir};
		String[] path3 = {coOccurrenceMatrixDir, normalizeDir};
		String[] path4 = {normalizeDir, averageRatingDir, multiplicationDir};
		String[] path5 = {multiplicationDir, sumDir};
		String[] path6 = {rawInput, averageRatingDir, movieIDStart, movieIDSize};
		
		dataDividerByUser.main(path1); //17
		averageRating.main(path6);  //18
		coOccurrenceMatrixGenerator.main(path2); //19
		normalize.main(path3); //20
		multiplication.main(path4);//21
		sum.main(path5); //22


	}

}
