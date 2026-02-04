# Taller práctico: Git Flow

Oficina de Tecnología y Transformación Digital
Secretaría de Hábitat

Objetivo
- Aprender y practicar el modelo de ramificación Git Flow: feature, release y hotfix.

Prerequisitos
- Git instalado.
- Repositorio remoto configurado y clone local.
- main protegido (recomendado).

Preparación inicial
1. Clonar y posicionarse en main:
   git clone <repo-url>
   cd <repo>
   git checkout main
   git pull
2. Crear publish develop si no existe:
   git checkout -b develop
   git push -u origin develop

Nota importante: antes de crear ramas o PR, verifique la sección "Restricciones de ramas" y la "Checklist obligatorio antes de PR" en este documento.

Ejercicio 1 — Features paralelos
- Crear dos ramas: feature/login y feature/api desde develop.
Pasos:
1. feature/login:
   git checkout develop
   git checkout -b feature/login
   # cambios
   git commit -am "feat(login): implementación inicial"
   git push -u origin feature/login
2. feature/api:
   git checkout develop
   git checkout -b feature/api
   # cambios
   git commit -am "feat(api): endpoint ejemplo"
   git push -u origin feature/api
3. Abrir PRs hacia develop y verificar merges.

Ejercicio 2 — Preparar release
- Crear release/1.0.0 desde develop y estabilizar:
   git checkout develop
   git checkout -b release/1.0.0
   # fixes de QA
   git commit -am "chore(release): ajustes finales 1.0.0"
   git push -u origin release/1.0.0

Ejercicio 3 — Publicar release
1. Merge release a main:
   git checkout main
   git merge --no-ff release/1.0.0 -m "Merge release 1.0.0"
   git push origin main
2. Tag y push:
   git tag -a v1.0.0 -m "v1.0.0"
   git push origin v1.0.0
3. Merge release a develop:
   git checkout develop
   git merge --no-ff release/1.0.0 -m "Merge release 1.0.0 to develop"
   git push origin develop
4. Eliminar rama release si aplica:
   git branch -d release/1.0.0
   git push origin --delete release/1.0.0

Ejercicio 4 — Hotfix desde main
1. Crear hotfix/1.0.1 desde main:
   git checkout main
   git checkout -b hotfix/1.0.1
   # corregir bug
   git commit -am "fix(hotfix): corregir bug crítico"
   git push -u origin hotfix/1.0.1
2. Merge hotfix a main y tag:
   git checkout main
   git merge --no-ff hotfix/1.0.1 -m "Merge hotfix 1.0.1"
   git tag -a v1.0.1 -m "v1.0.1"
   git push origin main
   git push origin v1.0.1
3. Merge hotfix a desarroll y limpiar:
   git checkout develop
   git merge --no-ff hotfix/1.0.1 -m "Merge hotfix 1.0.1 to develop"
   git push origin develop
   git branch -d hotfix/1.0.1
   git push origin --delete hotfix/1.0.1

Comandos útiles
- Ver ramas: git branch -a
- Historial gráfico: git log --oneline --graph --all --decorate
- Ver tags: git tag --list

Apéndice: comandos Git usados y explicación breve
- git clone <repo-url>
  Clona el repositorio remoto a tu máquina local.
- cd <repo>
  Entra en el directorio del repositorio clonado.
- git checkout <branch>
  Cambia a la rama indicada (ej: main, develop).
- git pull origin <branch>
  Trae y mezcla cambios desde el remoto para mantener la rama actualizada.
- git checkout -b <new-branch>
  Crea y cambia a una nueva rama basada en la rama actual.
- git add <files> (o git add .)
  Prepara los cambios para commit (staging).
- git commit -m "mensaje"  ó git commit -am "mensaje"
  Crea un commit con los cambios preparados; `-am` agrega y commitea archivos rastreados en un paso.
- git push -u origin <branch>
  Publica la rama en el remoto y establece seguimiento.
- git push origin <branch>
  Envía commits de la rama local al remoto.
- git merge --no-ff <branch>
  Fusiona la rama indicada en la actual creando un commit de merge (sin fast-forward).
- git tag -a <tag> -m "mensaje"
  Crea un tag anotado para marcar versiones importantes.
- git push origin <tag>
  Publica el tag al remoto.
- git branch -d <branch>
  Elimina una rama local que ya fue mergeada.
- git push origin --delete <branch>
  Elimina una rama en el remoto.
- git log --oneline --graph --all --decorate
  Muestra un historial compacto y gráfico de commits en todas las ramas.
- git branch -a
  Lista ramas locales y remotas.
- git tag --list
  Lista tags existentes.
- git fetch
  Trae cambios del remoto sin mezclarlos en la rama actual (útil para revisar antes de hacer pull o rebase).
- git rebase <base-branch>
  Reaplica commits de la rama actual sobre la punta de otra rama para mantener historia más lineal (usar con cuidado en ramas compartidas).
- git status
  Muestra el estado actual del working tree: cambios sin stage, archivos no rastreados y rama actual.

Restricciones de ramas (PROTECCIONES OBLIGATORIAS)
- Ramas protegidas: main y develop deben estar protegidas en el repositorio remoto.
- Prohibir commits directos: deshabilitar push directo a main y develop (sólo merges vía PR).
- Pull Request obligatorio: todo cambio hacia develop o main debe realizarse mediante PR.
- Revisiones: exigir al menos 1 revisor aprobado (recomendado 2 para main).
- CI obligatorio: el pipeline/Checks deben pasar antes de permitir el merge.
- Requerir merge strategy controlada: preferir merge --no-ff o merge por PR con squash según política del proyecto.
- Prohibir force-push y borrar ramas protegidas: deshabilitar force-push a main/develop y evitar borrado accidental.
- Requerir descripción y checklist en PR: incluir objetivos, pruebas realizadas y ticket asociado.

Checklist obligatorio antes de PR
- Rama creada desde la rama base correcta (feature/* desde develop, release/* desde develop, hotfix/* desde main).
- Hacer pull y rebase si es necesario para mantenerla actualizada con la rama base.
- Commits atómicos y mensajes según convención.
- Ejecutar pruebas locales y linters; adjuntar resumen en el PR.
- Asegurar que la rama no contiene cambios no relacionados.
- Incluir ticket o referencia en la descripción del PR.
- Adjuntar evidencia mínima de pruebas (logs, capturas o salida de tests).
- Verificar que la rama protegida (develop/main) tiene reglas activas (ver guía siguiente).

Cómo verificar/configurar protecciones de rama (rápido, GitHub)
1. Ir a: Repository -> Settings -> Branches -> Branch protection rules.
2. Confirmar que existe una regla para 'main' y otra para 'develop'.
3. Opciones obligatorias a activar:
   - Require pull request reviews before merging (1 o 2 revisores).
   - Require status checks to pass before merging (marcar los pipelines relevantes).
   - Include administrators (opcional según política).
   - Restrict who can push to matching branches (restringir pushes directos).
   - Require linear history o bloquear force-push según política.
4. Guardar y documentar la configuración (captura o enlace) para la validación del taller.

Plantilla de PR (ejemplo obligatorio)
Título: [tipo] <breve-descripción> (ej: feat(login): añadir validación de email)
Descripción:
- Objetivo: <qué cambia y por qué>
- Ticket: <ID del ticket o enlace>
- Cambios principales: lista corta de archivos y puntos clave
- Pruebas realizadas: <resumen de tests y pasos para reproducir>
- Checklist:
  - [ ] Compilación y tests locales pasan
  - [ ] CI verde en la rama del PR
  - [ ] Revisores asignados
  - [ ] No hay cambios no relacionados

Buenas prácticas (obligatorias)
- No commits directos a main.
- PR obligatorio para develop/main.
- CI debe pasar antes del merge.
- Nombres de ramas: feature/*, release/*, hotfix/* como en los ejercicios.
- Mensajes de commit siguiendo convención (tipo(scope): descripción) — ej: feat(login): ...
- Mantener ramas pequeñas y centradas en una sola tarea.

Criterios de entrega
- Ramas creadas y pusheadas: develop, feature/login, feature/api, release/1.0.0, hotfix/1.0.1.
- main debe tener tags v1.0.0 y v1.0.1.
- develop debe contener merges de features y del hotfix.
- Documentar en el PR los pasos realizados y pruebas ejecutadas.

Validación adicional para el taller (obligatoria)
- Captura de la configuración de protecciones en el repositorio (pantallazo o enlace a la sección de Branch protection).
- PRs deben mostrar revisores y checks pasados antes del merge.

Fin del taller
