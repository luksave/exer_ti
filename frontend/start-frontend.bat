@echo off
echo Iniciando o frontend Angular...
echo.

echo ========================================
echo Instalando dependências
echo ========================================
npm install

echo.
echo ========================================
echo Iniciando servidor de desenvolvimento
echo ========================================
echo Frontend estará disponível em: http://localhost:4200
echo.
echo Pressione Ctrl+C para parar o servidor
echo.

npm start
