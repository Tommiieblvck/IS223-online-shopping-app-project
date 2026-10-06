$dir = "C:\Users\USER832\AndroidStudioProjects\Theodistonlineshoppingapp\app\src\main\res\drawable"
$mapping = @{
    "Calculator.jpg" = "calculator.jpg"
    "Highlighters.jpg" = "highlighters.jpg"
    "Office Desk.jpg" = "office_desk.jpg"
    "Office Chair.jpg" = "office_chair.jpg"
    "Workstations.jpg" = "workstations.jpg"
    "Filing_Storage.jpeg" = "filing_storage.jpeg"
}
foreach ($k in $mapping.Keys) {
    $src = Join-Path $dir $k
    $dst = Join-Path $dir $mapping[$k]
    $tmp = Join-Path $dir "tmp_$(Get-Random)"
    if (Test-Path $src) {
        Move-Item $src $tmp -Force
        Move-Item $tmp $dst -Force
    }
}
