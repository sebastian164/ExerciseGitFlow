# Taller práctico: Git Flow

**Oficina de Tecnología y Transformación Digital — Secretaría de Hábitat**

---

## Objetivo

- Aprender y practicar el modelo de ramificación Git Flow: feature, release y hotfix.
- Garantizar conocimiento de protecciones de ramas y flujo de PRs.

## Prerrequisitos

- Git instalado.
- Repositorio remoto configurado y clone local.
- main protegido (recomendado).

---

## Preparación inicial

1. Clonar y posicionarse en main:
   - git clone <repo-url>
   - cd <repo>
   - git checkout main
   - git pull
2. Crear y publicar la rama develop si no existe:
   - git checkout -b develop
   - git push -u origin develop

> Nota: antes de crear ramas o PR, verifique la sección "Restricciones de ramas" y la "Checklist obligatorio antes de PR".

---

## Ejercicios

### Ejercicio 1 — Features paralelos

Objetivo: crear dos features y aprender el flujo de trabajo con PRs.

Pasos:
1. feature/login:
   - git checkout develop
   - git checkout -b feature/login
   - (realizar cambios en los archivos necesarios)
   - git commit -am "feat(login): implementación inicial"
   - git push -u origin feature/login
2. feature/api:
   - git checkout develop
   - git checkout -b feature/api
   - (realizar cambios en los archivos necesarios)
   - git commit -am "feat(api): endpoint ejemplo"
   - git push -u origin feature/api
3. Abrir Pull Requests hacia develop y verificar merges.


### Ejercicio 2 — Preparar release

Objetivo: generar release/1.0.0 desde develop y estabilizar.

Pasos:
- git checkout develop
- git checkout -b release/1.0.0
- (realizar correcciones de QA en esta rama)
- git commit -am "chore(release): ajustes finales 1.0.0"
- git push -u origin release/1.0.0


### Ejercicio 3 — Publicar release

Pasos:
1. Hacer merge del release en main (en la plataforma):
   - En la plataforma (GitHub/GitLab/Bitbucket) crear y aceptar un PR desde release/1.0.0 → main. Asegurar revisiones y checks.
   - Luego actualizar localmente:
     - git checkout main
     - git pull origin main
2. Hacer merge del release en develop (en la plataforma):
   - En la plataforma crear y aceptar un PR desde release/1.0.0 → develop. Asegurar revisiones y checks.
   - Luego actualizar localmente:
     - git checkout develop
     - git pull origin develop
3. Nota: en este taller no se eliminarán ramas automáticamente; si la política del repositorio lo permite, eliminar release en plataforma es opcional.


### Ejercicio 4 — Hotfix desde main

Objetivo: simular corrección urgente en producción.

Pasos:
1. Crear hotfix/1.0.1 desde main:
   - git checkout main
   - git checkout -b hotfix/1.0.1
   - (corregir el bug crítico en los archivos correspondientes)
   - git commit -am "fix(hotfix): corregir bug crítico"
   - git push -u origin hotfix/1.0.1
2. Hacer merge del hotfix en main (en la plataforma):
   - En la plataforma crear y aceptar un PR desde hotfix/1.0.1 → main. Asegurar revisiones y checks.
   - Luego actualizar localmente:
     - git checkout main
     - git pull origin main
3. Hacer merge del hotfix en develop (en la plataforma) y sincronizar local:
   - En la plataforma crear y aceptar un PR desde hotfix/1.0.1 → develop. Asegurar revisiones y checks.
   - Luego actualizar localmente:
     - git checkout develop
     - git pull origin develop
   - Nota: no se eliminarán ramas automáticamente en este taller

---

## Comandos útiles

- Ver ramas: git branch -a
- Historial gráfico: git log --oneline --graph --all --decorate


## Apéndice: comandos Git usados y explicación breve

- git clone <repo-url>
  - Clona el repositorio remoto a tu máquina local.
- cd <repo>
  - Entra en el directorio del repositorio clonado.
- git checkout <branch>
  - Cambia a la rama indicada (ej: main, develop).
- git pull origin <branch>
  - Trae y mezcla cambios desde el remoto para mantener la rama actualizada.
- git checkout -b <new-branch>
  - Crea y cambia a una nueva rama basada en la rama actual.
- git add <files> (o git add .)
  - Prepara los cambios para commit (staging).
- git commit -m "mensaje"  ó git commit -am "mensaje"
  - Crea un commit con los cambios preparados; `-am` agrega y commitea archivos rastreados en un paso.
- git push -u origin <branch>
  - Publica la rama en el remoto y establece seguimiento.
- git push origin <branch>
  - Envía commits de la rama local al remoto.
- git merge --no-ff <branch>
  - Fusiona la rama indicada en la actual creando un commit de merge (sin fast-forward).
- git branch -d <branch>
  - Elimina una rama local que ya fue mergeada.
- git push origin --delete <branch>
  - Elimina una rama en el remoto.
- git log --oneline --graph --all --decorate
  - Muestra un historial compacto y gráfico de commits en todas las ramas.
- git branch -a
  - Lista ramas locales y remotas.
- git fetch
  - Trae cambios del remoto sin mezclarlos en la rama actual (útil para revisar antes de hacer pull o rebase).
- git rebase <base-branch>
  - Reaplica commits de la rama actual sobre la punta de otra rama para mantener historia más lineal (usar con cuidado en ramas compartidas).
- git status
  - Muestra el estado actual del working tree: cambios sin stage, archivos no rastreados y rama actual.

---

## Restricciones de ramas (PROTECCIONES OBLIGATORIAS)

- Ramas protegidas: main y develop deben estar protegidas en el repositorio remoto.
- Prohibir commits directos: deshabilitar push directo a main y develop (sólo merges vía PR).
- Pull Request obligatorio: todo cambio hacia develop o main debe realizarse mediante PR.
- Revisiones: exigir al menos 1 revisor aprobado (recomendado 2 para main).
- CI obligatorio: el pipeline/Checks deben pasar antes de permitir el merge.
- Requerir merge strategy controlada: preferir merge --no-ff o merge por PR con squash según política del proyecto.
- Prohibir force-push y borrar ramas protegidas: deshabilitar force-push a main/develop y evitar borrado accidental.
- Requerir descripción y checklist en PR: incluir objetivos, pruebas realizadas y ticket asociado.

---

## Checklist obligatorio antes de PR

- Rama creada desde la rama base correcta (feature/* desde develop, release/* desde develop, hotfix/* desde main).
- Hacer pull y rebase si es necesario para mantenerla actualizada con la rama base.
- Commits atómicos y mensajes según convención.
- Ejecutar pruebas locales y linters; adjuntar resumen en el PR.
- Asegurar que la rama no contiene cambios no relacionados.
- Incluir ticket o referencia en la descripción del PR.
- Adjuntar evidencia mínima de pruebas (logs, capturas o salida de tests).
- Verificar que la rama protegida (develop/main) tiene reglas activas (ver guía siguiente).

---

## Cómo verificar/configurar protecciones de rama (rápido, GitHub)

1. Ir a: Repository -> Settings -> Branches -> Branch protection rules.
2. Confirmar que existe una regla para 'main' y otra para 'develop'.
3. Opciones obligatorias a activar:
   - Require pull request reviews before merging (1 o 2 revisores).
   - Require status checks to pass before merging (marcar los pipelines relevantes).
   - Include administrators (opcional según política).
   - Restrict who can push to matching branches (restringir pushes directos).
   - Require linear history o bloquear force-push según política.
4. Guardar y documentar la configuración (captura o enlace) para la validación del taller.

---

## Plantilla de PR (ejemplo obligatorio)

Título: [tipo] <breve-descripción> (ej: feat(login): añadir validación de email)

Descripción:
- Objetivo: <qué cambia y por qué>
- Ticket: <ID del ticket o enlace>
- **Cambios principales:** lista corta de archivos y puntos clave
- Pruebas realizadas: <resumen de tests y pasos para reproducir>

Checklist:
  - [ ] Compilación y tests locales pasan
  - [ ] CI verde en la rama del PR
  - [ ] Revisores asignados
  - [ ] No hay cambios no relacionados

---

## Buenas prácticas (obligatorias)

- No commits directos a main.
- PR obligatorio para develop/main.
- CI debe pasar antes del merge.
- Nombres de ramas: feature/*, release/*, hotfix/* como en los ejercicios.
- Mensajes de commit siguiendo convención (tipo(scope): descripción) — ej: feat(login): ...
- Mantener ramas pequeñas y centradas en una sola tarea.

---

## Criterios de entrega

- Ramas creadas y pusheadas: develop, feature/login, feature/api, release/1.0.0, hotfix/1.0.1.
- develop debe contener merges de features y del hotfix.
- Documentar en el PR los pasos realizados y pruebas ejecutadas.


---

## Fin del taller
