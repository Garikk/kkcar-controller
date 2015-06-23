# KKCar
===============

Основная программа проекта, управление плагинами и системное меню

В данный момент проект состоит из предконфигурированых плагинов, обеспечивающих функционал вывода нескольких параметров диагностики ODB2 

Составные части проекта:

kkcar-base (https://github.com/Garikk/kkcar-plugin-datadisplay)
  Общий набор классов с базовым функционалом, обеспечивающий связь плагинов

kkcar-controller (данный репозиторий)
  Основная программа проекта, связывающая плагины также обеспечивает работы системного меню

kkcar-plugin-datadisplay (https://github.com/Garikk/kkcar-plugin-datadisplay)
  Плагин обработки ODB2 данных для дальшейшего вывода на экран или в отчёт
  
kkcar-plugin-odb2 (https://github.com/Garikk/kkcar-plugin-odb2)
  Плагин чтения данных ODB2 непосредственно из источника (в данный момент только ELM327)
  
kkcar-plugin-controls (https://github.com/Garikk/kkcar-plugin-controls)
  Плагин обеспечивающий обработку событий от внешних физических источников (в данный момент обработка кнопок через GPIO RPI_B(v1)

kkcar-plugin-leddisplay (https://github.com/Garikk/kkcar-plugin-leddisplay)
  Плагин обеспечивающий вывод текстовых данных на экран (в данный момент текстовый 2x16)

kkcar-plugin-webdatasync (https://github.com/Garikk/kkcar-plugin-webdatasync)
  Плагин связи с сайтом KKCar webmaster, пока не функционален
