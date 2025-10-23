@echo off
echo Executando testes unitários da API...
echo.

echo ========================================
echo Executando todos os testes
echo ========================================
mvn test

echo.
echo ========================================
echo Executando testes com relatório de cobertura
echo ========================================
mvn test jacoco:report

echo.
echo ========================================
echo Testes concluídos!
echo ========================================
pause
