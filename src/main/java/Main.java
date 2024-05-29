import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.DARKGRAY;
import static com.raylib.Jaylib.LIGHTGRAY;
import static com.raylib.Jaylib.BLUE;
import static com.raylib.Raylib.*;

public class Main {
    public static void main(String[] args){
        
        int size = 16;
        Conway Cnw = new Conway(size);
        Cnw.cnwTestShip();

        final float csize = 0.55f;

        InitWindow(800, 600, "Jaylib's Game of Life");
        SetTargetFPS(60);
        Camera3D camera = new Camera3D()
                ._position(new Vector3().x(0).y(20).z(size))
                .target(new Vector3())
                .up(new Vector3().x(0).y(1).z(0))
                .fovy(45).projection(CAMERA_PERSPECTIVE);

        float tcsize = 0;
        
        boolean gameStart = false;
        Rectangle startBtn = new Rectangle().width(200).height(50);

        // pallete

        Color clrLive = BLUE;
        Color clrDead = LIGHTGRAY;
        Color[] clrInterface = new Color[]{
                new Color()
                        .r((byte) 130)
                        .g((byte) 130)
                        .b((byte) 240)
                        .a((byte) 255),
                new Color()
                        .r((byte) 70)
                        .g((byte) 70)
                        .b((byte) 200)
                        .a((byte) 255),
        };


        while (!WindowShouldClose()) {

            BeginDrawing();

            if (gameStart)
                ClearBackground(DARKGRAY);
            else
                ClearBackground(RAYWHITE);

            BeginMode3D(camera);
            
            // conways
            if (gameStart)
                {
                    DrawGrid(size, 1.0f);
                    // Desenhar toda célula da grid.
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            if (Cnw.cnwGetCell(j, i))   // True -> célula viva.
                                DrawCube(new Vector3().x(j - (size / 2f - .5f)).y(0).z(i - size / 2f + .5f), tcsize, tcsize, tcsize, clrLive);

                            else
                                DrawCube(new Vector3().x(j - (size / 2f - .5f)).y(0).z(i - size / 2f + .5f), csize/2, csize/2, csize/2, clrDead);
                        }
                    }

                if (tcsize < csize) {
                    tcsize += 0.05f;
                    clrLive.a((byte) ((tcsize / csize > 1 ? 1 : tcsize / csize) * 250));
                }

                if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
                    Cnw.cnwUpdate();
                    tcsize = csize / 2 - 0.05f;
                    clrLive.a((byte) 0);
                }
            }

            EndMode3D();

            // Menu Inicial
            if (!gameStart){

                DrawText("JayLib Conways Game of Life", ((GetScreenWidth() - MeasureText("JayLib Conways Game of Life",30))/2), GetScreenHeight()/3, 30, clrInterface[0]);
                startBtn.x((GetScreenWidth()-startBtn.width())/2).y(GetScreenHeight()*2/3);


                if (CheckCollisionPointRec(GetMousePosition(), startBtn)) {
                    DrawRectangle((int)startBtn.x(), (int)startBtn.y(), (int)startBtn.width(), (int)startBtn.height(), clrInterface[1]);
                    DrawText("Start", (int)(startBtn.x() + (startBtn.width() - MeasureText("Start",20))/2), (int) (startBtn.y() - 3 + startBtn.height()/2), 20, clrInterface[0]);
                    // Cor on-hover habilitada

                    if (IsMouseButtonReleased(MOUSE_BUTTON_LEFT))
                        gameStart = true;

                }
                else {
                    DrawRectangle((int) startBtn.x(), (int) startBtn.y(), (int) startBtn.width(), (int) startBtn.height(), clrInterface[0]);
                    DrawText("Start", (int) (startBtn.x() + (startBtn.width() - MeasureText("Start", 20)) / 2), (int) (startBtn.y() - 3 + startBtn.height() / 2), 20, clrInterface[1]);
                    // Cor on-hover desabilitada

                }
            }

            EndDrawing();
        }
        CloseWindow();
    }
}