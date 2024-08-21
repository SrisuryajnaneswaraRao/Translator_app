<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Translator</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<style>
h3 {
	padding-left: 40%;
	font-style: italic;
	font-weight: 200rem;
}
</style>
</head>
<body>
	<div
		class="container mt-5 border border-primary rounded shadow p-5 bg-white"
		style="transform: translateY(13%); width: 60vw;">
		<form action="TranslateServlet" method="post">
			<h2 class="text-primary text-center">Welcome To Translator App</h2>
			<div class="my-2">
				<label class="form-label d-block">Enter text here</label>
				<textarea placeholder="Enter your text here" name="texttotranslate"
					class="form-control"></textarea>
			</div>

			<div class="mb-2">
				<label class="form-label d-block">Select language to
					translate</label> <select class="form-select" name="languageselected">
					<option value="es">Spanish</option>
					<option value="fr">French</option>
					<option value="hi">Hindi</option>
					<option value="te">Telugu</option>
				</select>
			</div>

			<div class="row align-items-end">
				<div class="col">
					<button class="btn btn-primary d-block mx-auto mt-3 px-4 py-2"
						type="submit">Translate</button>
				</div>
				<div class="col">
					<a href='/Translator_app/TranslateRecordServlet'
						class='btn btn-success mx-2 px-5'>View All Records</a>
				</div>
			</div>
		</form>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
</body>
</html>